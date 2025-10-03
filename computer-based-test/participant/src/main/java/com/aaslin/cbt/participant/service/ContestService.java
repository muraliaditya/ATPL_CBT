package com.aaslin.cbt.participant.service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aaslin.cbt.common.model.*;
import com.aaslin.cbt.participant.dto.*;
import com.aaslin.cbt.participant.exception.CustomException;
import com.aaslin.cbt.participant.exception.CustomException.InternalServerException;
import com.aaslin.cbt.participant.exception.CustomException.ParticipantValidationException;
import com.aaslin.cbt.participant.repository.*;
import com.aaslin.cbt.participant.util.CustomIdGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;



/**@author ATPLD14
 * 
 */
@Service("participantContestService")
@AllArgsConstructor
public class ContestService {

    private final ContestRepository contestRepo;
    private final MCQRepository mcqRepository;
    private final CodingQuestionsRepository codingRepository;
    private final SubmissionRepository submissionRepository;
    private final CustomIdGenerator customIdGenerator;
    private final ParticipantRepository participantRepository;
    private final McqAnswersRepository mcqAnswerRepository;
    private final CodingSubmissionRepository codingSubmissionRepository;
    private final TestCaseRepository testcaseRepo;
    private final ObjectMapper objectMapper;


    public ContestEligibilityResponse checkEligibility(String contestId) throws InternalServerException {
        try {
            Contest contest = contestRepo.findById(contestId)
                    .orElseThrow(() -> new CustomException.ContestNotFoundException("Contest not found with id: " + contestId));
            return new ContestEligibilityResponse(contest.getContestId(), contest.getCategory().getCategoryName());
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException.InternalServerException("Error while checking contest eligibility", e);
        }
    }

    public ContestDetailsResponse getContestInfo(String contestId) throws InternalServerException {
        try {
            Contest contest = contestRepo.findById(contestId)
                    .orElseThrow(() -> new CustomException.ContestNotFoundException("Contest not found with id: " + contestId));

            List<SubSectionResponse> subSections = Arrays.asList(
                    new SubSectionResponse("Quantitative", contest.getTotalQuantsMcqs(), contest.getTotalQuantsMcqs() * 2),
                    new SubSectionResponse("Reasoning", contest.getTotalReasoningMcqs(), contest.getTotalReasoningMcqs() * 2),
                    new SubSectionResponse("Technical", contest.getTotalTechnicalMcqs(), contest.getTotalTechnicalMcqs() * 2),
                    new SubSectionResponse("Verbal", contest.getTotalVerbalMcqs(), contest.getTotalVerbalMcqs() * 2)
            );

            int totalMcqCount = subSections.stream().mapToInt(SubSectionResponse::getQuestionCount).sum();
            int totalMcqMarks = subSections.stream().mapToInt(SubSectionResponse::getMarks).sum();

            List<SectionResponse> sections = Arrays.asList(
                    new SectionResponse("Coding", contest.getTotalCodingQuestions(), contest.getTotalCodingQuestions() * 50, null),
                    new SectionResponse("MCQ", totalMcqCount, totalMcqMarks, subSections)
            );

            return new ContestDetailsResponse(contest.getContestName(), sections);

        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException.InternalServerException("Error while fetching contest details", e);
        }
    }

    public ContestStartResponse startContest(String contestId) throws InternalServerException {
        try {
            Contest contest = contestRepo.findById(contestId)
                    .orElseThrow(() -> new CustomException.ContestNotFoundException("Contest not found with id: " + contestId));

            Map<Section, List<McqQuestion>> mcqBySection = mcqRepository.findMcqQuestionsByContestId(contestId)
                    .stream()
                    .collect(Collectors.groupingBy(McqQuestion::getSection));

            List<MCQSection> mcqSections = mcqBySection.entrySet().stream()
                    .map(entry -> new MCQSection(
                            entry.getKey(),
                            entry.getValue().stream()
                                    .map(q -> new McqQuestionDto(
                                            q.getMcqQuestionId(),
                                            q.getQuestionText(),
                                            q.getOption1(),
                                            q.getOption2(),
                                            q.getOption3(),
                                            q.getOption4(),
                                            q.getWeightage()
                                    ))
                                    .collect(Collectors.toList())
                    ))
                    .collect(Collectors.toList());

            List<CodingQuestionDto> codingQuestions = codingRepository.findCodingQuestionsByContestId(contestId)
                    .stream()
                    .map(q -> {
                        List<String> inputParams=null;
                        List<String> inputType=null;
						try {
							inputParams = parseJsonArray(q.getInputParams());
							inputType=parseJsonArray(q.getInputType());

						} catch (InternalServerException severException) {

							severException.printStackTrace();
						}
                        
                        List<TestcasesDTO> testcaseDTOs = testcaseRepo.findByCodingQuestion_CodingQuestionId(q.getCodingQuestionId())
                                .stream()
                                .map(tc -> new TestcasesDTO(
                                        tc.getTestcaseId(),
                                        tc.getInputValues(),
                                        tc.getExpectedOutput(),
                                        tc.getTestcaseType().name(),
                                        tc.getWeightage(),
                                        tc.getDescription()
                                ))
                                .collect(Collectors.toList());

                        return new CodingQuestionDto(
                                q.getCodingQuestionId(),
                                q.getQuestion(),
                                q.getDescription(),
                                q.getJavaBoilerCode(),
                                q.getPythonBoilerCode(),
                                inputParams,
                                inputType,
                                q.getOutputFormat(),
                                testcaseDTOs
                        );
                    })
                    .collect(Collectors.toList());

            return new ContestStartResponse(
                    contest.getContestId(),
                    contest.getContestName(),
                    contest.getStatus().name(),
                    contest.getStartTime(),
                    contest.getEndTime(),
                    contest.getDuration(),
                    contest.getCategory(),
                    new MCQResponse(mcqSections),
                    new CodingResponse(codingQuestions)
            );

        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            throw new CustomException.InternalServerException("Error while starting contest", e);
        }
    }

    private List<String> parseJsonArray(String json) throws InternalServerException {
        try {
            JsonNode root = objectMapper.readTree(json);
            if (root.isArray()) {
                return objectMapper.convertValue(root, new TypeReference<List<String>>() {});
            }
            return List.of();
        } catch (Exception severException) {
            throw new CustomException.InternalServerException("Failed to parse JSON: " + json, severException);
        }
    }

    @Transactional
    public void handleCodingSubmissions(Submission submission, TestSubmissionRequest request) throws InternalServerException {
        try {
            List<CodingSubmission> latestSubmissions = codingSubmissionRepository
                    .findBySubmission_Participant_ParticipantIdAndSubmission_Contest_ContestId(request.getParticipantId(), request.getContestId());

            int totalCodingScore = latestSubmissions.stream()
                    .mapToInt(CodingSubmission::getScore)
                    .sum();

            submission.setTotalCodingScore(totalCodingScore);
            submissionRepository.save(submission);

        } catch (Exception severException) {
            throw new CustomException.InternalServerException("Error while handling coding submissions", severException);
        }
    }

    @Transactional
    public TestSubmissionResponse saveTestSubmission(TestSubmissionRequest request) throws InternalServerException {
        try {
            Participant participant = participantRepository.findById(request.getParticipantId())
                    .orElseThrow(() -> new ParticipantValidationException("Participant not found"));

            Contest contest = contestRepo.findById(request.getContestId())
                    .orElseThrow(() -> new CustomException.ContestNotFoundException("Contest not found"));

            Submission submission = submissionRepository.findByParticipant_ParticipantIdAndContest_ContestId(
                    request.getParticipantId(),
                    request.getContestId()
            ).orElseGet(() -> {
                Submission newSubmission = new Submission();
                newSubmission.setSubmissionId(customIdGenerator.generateSubmissionId());
                newSubmission.setParticipant(participant);
                newSubmission.setContest(contest);
                return newSubmission;
            });

            submission.setSubmittedAt(LocalDateTime.now());
            submission.setUpdatedAt(LocalDateTime.now());
            submission.setTotalMcqScore(0);
            submission.setTotalScore(0);
            submissionRepository.save(submission);

            int totalMcqScore = 0;
            if (request.getMcqAnswers() != null) {
                for (Map.Entry<String, String> entry : request.getMcqAnswers().entrySet()) {
                    String mcqId = entry.getKey();
                    String selectedOption = entry.getValue();

                    MapContestMcq mapContestMcq = mcqRepository.findById(mcqId)
                            .orElseThrow(() -> new CustomException.MCQNotFoundException("MCQ not found: " + mcqId));
                    McqQuestion question = mapContestMcq.getMcqQuestion();

                    boolean correct = question.getAnswerKey() != null &&
                            question.getAnswerKey().equalsIgnoreCase(selectedOption);
                    int score = correct ? question.getWeightage() : 0;
                    totalMcqScore += score;

                    McqAnswer mcqAnswer = new McqAnswer();
                    mcqAnswer.setMcqAnswerId(customIdGenerator.generateAnswerId());
                    mcqAnswer.setSubmission(submission);
                    mcqAnswer.setMcqQuestion(question);
                    mcqAnswer.setSelectedOption(selectedOption);
                    mcqAnswer.setIsCorrect(correct);

                    mcqAnswerRepository.save(mcqAnswer);
                }
            }

            submission.setTotalMcqScore(totalMcqScore);
            handleCodingSubmissions(submission, request);

            int codingScore = submission.getTotalCodingScore();
            int totalScore = totalMcqScore + codingScore;

            submission = submissionRepository.findById(submission.getSubmissionId())
                    .orElseThrow(() -> new CustomException.SubmissionNotFoundException("Submission not found"));

            return TestSubmissionResponse.builder()
                    .submissionId(submission.getSubmissionId())
                    .submittedAt(submission.getSubmittedAt())
                    .totalMcqScore(totalMcqScore)
                    .totalCodingScore(codingScore)
                    .score(totalScore)
                    .status("SUCCESS")
                    .message("MCQ and Coding Submission successfully recorded and evaluated.")
                    .build();

        } catch (CustomException e) {
            throw e;
        } catch (Exception severException) {
            throw new CustomException.InternalServerException("Error while saving test submission", severException);
        }
    }
}