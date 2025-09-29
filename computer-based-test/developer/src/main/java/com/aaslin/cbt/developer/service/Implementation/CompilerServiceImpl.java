package com.aaslin.cbt.developer.service.Implementation;

import com.aaslin.cbt.common.model.*;
import com.aaslin.cbt.developer.Dto.*;
import com.aaslin.cbt.developer.exceptions.CompilationException;
import com.aaslin.cbt.developer.exceptions.RuntimeExecutionException;
import com.aaslin.cbt.developer.repository.*;
import com.aaslin.cbt.developer.service.CompilerService;
import com.aaslin.cbt.developer.util.CustomIdGenerator;
import com.aaslin.cbt.developer.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CompilerServiceImpl implements CompilerService {

    private final DockerExecutor dockerExecutor;
    private final CodingQuestionRepository questionRepo;
    private final TestcaseRepository testcaseRepo;
    private final DeveloperCodingSubmissionRepository submissionRepo;
    private final DeveloperTestcaseResultsRepository resultRepo;
    private final UserRepository userRepo;
    private final LanguageTypeRepository languageTypeRepo;
    private final SubmissionUtils submissionUtils;

    public CompileRunResponseDto compileRun(String userId, String questionId, String languageType, String code) {
        List<Testcases> testcases = testcaseRepo.findByCodingQuestion_CodingQuestionIdAndTestcaseType(
                questionId, Testcases.TestcaseType.PUBLIC);

        List<PublicTestcaseResultDto> results = new ArrayList<>();
        int passed = 0;

        questionRepo.findById(questionId).orElseThrow();

        for (Testcases tc : testcases) {
            try {
                String output = dockerExecutor.executeUserCode(
                        languageType,
                        code,
                        questionId,
                        userId,
                        tc.getInputValues()
                );

                boolean isPassed = tc.getExpectedOutput().trim().equals(output.trim());

                results.add(new PublicTestcaseResultDto(
                        tc.getTestcaseId(),
                        tc.getInputValues(),
                        tc.getExpectedOutput(),
                        output,
                        isPassed ? "PASSED" : "FAILED",
                        tc.getWeightage()
                ));

                if (isPassed) passed++;

            } catch (CompilationException ce) {
                return new CompileRunResponseDto("COMPILATION_ERROR", ce.getMessage());
            } catch (RuntimeExecutionException re) {
               return new CompileRunResponseDto("RUNTIME_ERROR",re.getMessage());
            } catch (Exception e) {
                return new CompileRunResponseDto("ERROR", e.getMessage());
            }
        }

        String codeStatus = (passed == testcases.size()) 
                ? DeveloperCodingSubmissions.DeveloperCodingSubmissionStatus.SOLVED.name() 
                : DeveloperCodingSubmissions.DeveloperCodingSubmissionStatus.WRONG_ANSWER.name();

        return new CompileRunResponseDto(codeStatus, "Compiled and run successfully", passed, results);
    }


 
    public SubmitResponseDto submit(String userId, String questionId, String languageType, String code) {
        // Validate DB entries
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        CodingQuestions question = questionRepo.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + questionId));

        LanguageType lang = languageTypeRepo.findByLanguageType(languageType)
                .orElseThrow(() -> new RuntimeException("LanguageType not found: " + languageType));

        // Check if a submission already exists
        DeveloperCodingSubmissions submission = submissionRepo
                .findByUser_UserIdAndCodingQuestion_CodingQuestionId(userId, questionId)
                .orElse(null);

        if (submission == null) {
            // First submission: generate new DCSUB
            String lastSubmissionId = submissionRepo.findTopByOrderByDeveloperCodingSubmissionIdDesc()
                    .map(DeveloperCodingSubmissions::getDeveloperCodingSubmissionId)
                    .orElse(null);
            String newSubmissionId = CustomIdGenerator.generateNextId("DCSUB", lastSubmissionId);

            submission = new DeveloperCodingSubmissions();
            submission.setDeveloperCodingSubmissionId(newSubmissionId);
            submission.setCreatedAt(LocalDateTime.now());
        }

        // Update submission data
        submission.setUser(user);
        submission.setCodingQuestion(question);
        submission.setLanguageTypeId(lang);
        submission.setCode(code);
        submission.setSubmittedAt(LocalDateTime.now());
        submission.setUpdatedAt(LocalDateTime.now());
        submission.setCodeStatus(DeveloperCodingSubmissions.DeveloperCodingSubmissionStatus.SOLVED);
        submissionRepo.save(submission);

        // Fetch all testcases
        List<Testcases> testcases = testcaseRepo.findByCodingQuestion_CodingQuestionId(questionId);
        if (testcases.isEmpty()) {
            return new SubmitResponseDto("ERROR", "No testcases found for question: " + questionId);
        }

        List<PublicTestcaseResultDto> publicResults = new ArrayList<>();
        List<PrivateTestcaseResultDto> privateResults = new ArrayList<>();
        int publicPassed = 0;
        int privatePassed = 0;

        for (Testcases tc : testcases) {
            try {
                String output = dockerExecutor.executeUserCode(
                        lang.getLanguageType(),
                        code,
                        questionId,
                        userId,
                        tc.getInputValues()
                );

                boolean isPassed = tc.getExpectedOutput().trim().equals(output.trim());

                // Check if DTCR already exists for this submission and testcase
                DeveloperTestcaseResults result = resultRepo
                        .findByDeveloperCodingSubmission_DeveloperCodingSubmissionIdAndTestcase_TestcaseId(
                                submission.getDeveloperCodingSubmissionId(), tc.getTestcaseId())
                        .orElse(null);

                if (result == null) {
                    // First time: generate DTCR
                    String lastResultId = resultRepo.findTopByOrderByDeveloperTestcaseResultIdDesc()
                            .map(DeveloperTestcaseResults::getDeveloperTestcaseResultId)
                            .orElse(null);
                    String newResultId = CustomIdGenerator.generateNextId("DTCR", lastResultId);

                    result = new DeveloperTestcaseResults();
                    result.setDeveloperTestcaseResultId(newResultId);
                    result.setDeveloperCodingSubmission(submission);
                    result.setTestcase(tc);
                    result.setCreatedAt(LocalDateTime.now());
                }

                // Update DTCR
                result.setTestcaseStatus(isPassed
                        ? DeveloperTestcaseResults.DeveloperTestcaseStatus.PASSED
                        : DeveloperTestcaseResults.DeveloperTestcaseStatus.FAILED);
                result.setUpdatedAt(LocalDateTime.now());
                resultRepo.save(result);

                // Store results for response
                if (tc.getTestcaseType() == Testcases.TestcaseType.PUBLIC) {
                    publicResults.add(new PublicTestcaseResultDto(
                            tc.getTestcaseId(),
                            tc.getInputValues(),
                            tc.getExpectedOutput(),
                            output,
                            isPassed ? "PASSED" : "FAILED",
                            tc.getWeightage()
                    ));
                    if (isPassed) publicPassed++;
                } else {
                    privateResults.add(new PrivateTestcaseResultDto(
                            tc.getTestcaseId(),
                            isPassed ? "PASSED" : "FAILED",
                            tc.getWeightage()
                    ));
                    if (isPassed) privatePassed++;
                }
              } catch (CompilationException ce) {
                    
                    submission.setCodeStatus(DeveloperCodingSubmissions.DeveloperCodingSubmissionStatus.COMPILATION_ERROR);
                    submissionUtils.saveErrorResult(submission, tc, "COMPILATION_ERROR");
                    submissionUtils.addResponseResult(tc, publicResults, privateResults, "COMPILATION_ERROR", "FAILED");
                    
                } catch (RuntimeExecutionException re) {
                    submission.setCodeStatus(DeveloperCodingSubmissions.DeveloperCodingSubmissionStatus.RUNTIME_ERROR);
                    submissionUtils.saveErrorResult(submission, tc, "RUNTIME_ERROR");
                    submissionUtils.addResponseResult(tc, publicResults, privateResults, "RUNTIME_ERROR", "FAILED");
                    
                } catch (Exception e) {
                    submission.setCodeStatus(DeveloperCodingSubmissions.DeveloperCodingSubmissionStatus.RUNTIME_ERROR);
                    submissionUtils.saveErrorResult(submission, tc, "ERROR");
                    submissionUtils.addResponseResult(tc, publicResults, privateResults, "ERROR", "FAILED");
                }
            }
        
        int totalPassed = publicPassed + privatePassed;

        String codeStatus="";
        String message = "";
        if(submission.getCodeStatus() == DeveloperCodingSubmissions.DeveloperCodingSubmissionStatus.COMPILATION_ERROR) {
        	codeStatus = DeveloperCodingSubmissions.DeveloperCodingSubmissionStatus.COMPILATION_ERROR.name();
        	 message = "Compilation Error: Please fix your code syntax.";
        }
        else if (totalPassed == testcases.size()) {
            codeStatus = DeveloperCodingSubmissions.DeveloperCodingSubmissionStatus.SOLVED.name();
            message = "All testcases passed successfully!";
        } 
        else if((totalPassed < testcases.size()) && totalPassed != 0){
            codeStatus = DeveloperCodingSubmissions.DeveloperCodingSubmissionStatus.WRONG_ANSWER.name();
            message = "Some testcases failed. Please check your logic.";
        }
        else {
        	if(totalPassed == 0) {
        	codeStatus = DeveloperCodingSubmissions.DeveloperCodingSubmissionStatus.RUNTIME_ERROR.name();
        	message = "Runtime Error: Please check input/output handling or infinite loops.";
         }
        }


        submission.setPublicTestcasesPassed(publicPassed);
        submission.setPrivateTestcasesPassed(privatePassed);
        submission.setCodeStatus(DeveloperCodingSubmissions.DeveloperCodingSubmissionStatus.valueOf(codeStatus));
        submissionRepo.save(submission);

        // Return response
        return new SubmitResponseDto(
                codeStatus,
                message,
                publicPassed,
                privatePassed,
                publicResults,
                privateResults
        );
    }

}

