package com.aaslin.cbt.developer.service;

import com.aaslin.cbt.common.model.*;
import com.aaslin.cbt.developer.Dto.*;
import com.aaslin.cbt.developer.repository.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CompilerService {

    private final DockerExecutor dockerExecutor;
    private final CodingQuestionRepository questionRepo;
    private final TestcaseRepository testcaseRepo;
    private final DeveloperCodingSubmissionRepository submissionRepo;
    private final DeveloperTestcaseResultsRepository resultRepo;
    private final UserRepository userRepo;
    private final LanguageTypeRepository languageTypeRepo;

    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * /compile-run → only public testcases, no DB save
     */
    public CompileRunResponseDto compileRun(String developerId, String questionId, String language, String code) {
        List<Testcases> testcases =
                testcaseRepo.findByCodingQuestion_CodingQuestionIdAndTestcaseType(
                        questionId, Testcases.TestcaseType.PUBLIC);

        List<PublicTestcaseResultDto> results = new ArrayList<>();
        int passed = 0;

        CodingQuestions question = questionRepo.findById(questionId).orElseThrow();

        for (Testcases tc : testcases) {
            try {
                String output = dockerExecutor.runTemporaryCode(
                        language,
                        developerId,
                        questionId,
                        code,
                        question.getInputParams(),
                        tc.getInputValues()
                );

                boolean isPassed = compareOutput(output, tc.getExpectedOutput());

                results.add(new PublicTestcaseResultDto(
                        tc.getTestcaseId(),
                        tc.getInputValues(),
                        tc.getExpectedOutput(),
                        output,
                        isPassed ? "PASSED" : "FAILED",
                        tc.getWeightage()
                ));

                if (isPassed) passed++;
            } catch (Exception ex) {
                return new CompileRunResponseDto("COMPILATION_ERROR", ex.getMessage());
            }
        }

        return new CompileRunResponseDto("SUCCESS", null, passed, results);
    }

    /**
     * /submit → all testcases (public + private), save submission + results
     */
    public SubmitResponseDto submit(String developerId, String questionId, String languageId, String code) {
        List<Testcases> testcases = testcaseRepo.findByCodingQuestion_CodingQuestionId(questionId);

        User user = userRepo.findById(developerId).orElseThrow();
        CodingQuestions question = questionRepo.findById(questionId).orElseThrow();
        LanguageType lang = languageTypeRepo.findByLanguageType(languageId).orElseThrow();

        DeveloperCodingSubmissions submission = new DeveloperCodingSubmissions();
        submission.setDeveloperCodingSubmissionId(UUID.randomUUID().toString());
        submission.setUser(user);
        submission.setCodingQuestion(question);
        submission.setLanguageTypeId(lang);
        submission.setCode(code);
        submission.setSubmittedAt(LocalDateTime.now());
        submission.setCreatedAt(LocalDateTime.now());
        submission.setUpdatedAt(LocalDateTime.now());
        submission.setIsFinalAttempt(true);
        submission.setCodeStatus(DeveloperCodingSubmissions.DeveloperCodingSubmissionStatus.SOLVED);

        submissionRepo.save(submission);

        int publicPassed = 0;
        int privatePassed = 0;

        List<PublicTestcaseResultDto> publicResults = new ArrayList<>();
        List<PrivateTestcaseResultDto> privateResults = new ArrayList<>();

        for (Testcases tc : testcases) {
            try {
                String output = dockerExecutor.runTemporaryCode(
                        lang.getLanguageType(),
                        developerId,
                        questionId,
                        code,
                        question.getInputParams(),
                        tc.getInputValues()
                );

                boolean isPassed = compareOutput(output, tc.getExpectedOutput());

                // Save result
                DeveloperTestcaseResults result = new DeveloperTestcaseResults();
                result.setDeveloperTestcaseResultId(UUID.randomUUID().toString());
                result.setDeveloperCodingSubmission(submission);
                result.setTestcase(tc);
                result.setTestcaseStatus(isPassed ?
                        DeveloperTestcaseResults.DeveloperTestcaseStatus.PASSED :
                        DeveloperTestcaseResults.DeveloperTestcaseStatus.FAILED);
                result.setCreatedAt(LocalDateTime.now());
                result.setUpdatedAt(LocalDateTime.now());
                resultRepo.save(result);

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
            } catch (Exception ex) {
                return new SubmitResponseDto("COMPILATION_ERROR", ex.getMessage());
            }
        }

        submission.setPublicTestcasesPassed(publicPassed);
        submission.setPrivateTestcasesPassed(privatePassed);
        submissionRepo.save(submission);

        return new SubmitResponseDto("SUCCESS", null, publicPassed, privatePassed, publicResults, privateResults);
    }

    private boolean compareOutput(String actual, String expectedJson) {
        try {
            JsonNode expected = mapper.readTree(expectedJson).get("output");
            JsonNode actualNode = mapper.readTree(actual);
            return expected.equals(actualNode);
        } catch (Exception e) {
            return actual.trim().equals(expectedJson.trim());
        }
    }
}