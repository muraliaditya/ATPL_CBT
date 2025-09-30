package com.aaslin.cbt.participant.service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aaslin.cbt.common.model.*;
import com.aaslin.cbt.participant.dto.*;
import com.aaslin.cbt.participant.repository.*;
import com.aaslin.cbt.participant.util.CustomIdGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service("ParticipantContestsService")
public class ContestService {

    private final ContestRepository contestRepo;
    private final MCQRepository mcqRepository;
    private final CodingQuestionsRepository codingRepository;
    private final SubmissionRepository submissionRepository;
    private final CustomIdGenerator customIdGenerator;
    private final ParticipantRepository participantRepository;
    private final McqAnswersRepository mcqAnswerRepository;
    private final CodingSubmissionRepository codingSubmissionRepository;
    private final DockerExecutor dockerExecutorService;
    private final LanguageTypeRepository languageRepo;
    private final TestCaseRepository testcaseRepo;
    
    private ObjectMapper objectMapper;

  

    
	

	public ContestService(ContestRepository contestRepo, MCQRepository mcqRepository,
			CodingQuestionsRepository codingRepository, SubmissionRepository submissionRepository,
			CustomIdGenerator customIdGenerator, ParticipantRepository participantRepository,
			McqAnswersRepository mcqAnswerRepository, CodingSubmissionRepository codingSubmissionRepository,
			DockerExecutor dockerExecutorService, LanguageTypeRepository languageRepo, TestCaseRepository testcaseRepo,
			ObjectMapper objectMapper) {
		super();
		this.contestRepo = contestRepo;
		this.mcqRepository = mcqRepository;
		this.codingRepository = codingRepository;
		this.submissionRepository = submissionRepository;
		this.customIdGenerator = customIdGenerator;
		this.participantRepository = participantRepository;
		this.mcqAnswerRepository = mcqAnswerRepository;
		this.codingSubmissionRepository = codingSubmissionRepository;
		this.dockerExecutorService = dockerExecutorService;
		this.languageRepo = languageRepo;
		this.testcaseRepo = testcaseRepo;
		this.objectMapper = objectMapper;
	}

	public ContestEligibilityResponse checkEligibility(String contestId) {
        return contestRepo.findById(contestId)
                .map(contest -> new ContestEligibilityResponse(
                        contest.getContestId(),
                        contest.getCategory().getCategoryName()))
                .orElseThrow(() -> new IllegalArgumentException("Contest not found with id " + contestId));
    }

    public ContestDetailsResponse getContestInfo(String contestId) {
        Contest contest = contestRepo.findById(contestId)
                .orElseThrow(() -> new RuntimeException("Contest not found"));

        List<SectionResponse> sections = new ArrayList<>();
        sections.add(new SectionResponse("Coding",
                contest.getTotalCodingQuestions(),
                contest.getTotalCodingQuestions() * 50,
                null));

        List<SubSectionResponse> subSections = Arrays.asList(
                new SubSectionResponse("Quantitative", contest.getTotalQuantsMcqs(), contest.getTotalQuantsMcqs() * 2),
                new SubSectionResponse("Reasoning", contest.getTotalReasoningMcqs(), contest.getTotalReasoningMcqs() * 2),
                new SubSectionResponse("Technical", contest.getTotalTechnicalMcqs(), contest.getTotalTechnicalMcqs() * 2),
                new SubSectionResponse("Verbal", contest.getTotalVerbalMcqs(), contest.getTotalVerbalMcqs() * 2)
        );

        int totalMcqCount = subSections.stream().mapToInt(SubSectionResponse::getQuestionCount).sum();
        int totalMcqMarks = subSections.stream().mapToInt(SubSectionResponse::getMarks).sum();
        sections.add(new SectionResponse("MCQ", totalMcqCount, totalMcqMarks, subSections));

        return new ContestDetailsResponse(contest.getContestName(), sections);
    }

    public ContestStartResponse startContest(String contestId) {
        Contest contest = contestRepo.findById(contestId)
                .orElseThrow(() -> new RuntimeException("Contest not found"));

        Map<Sections, List<McqQuestions>> mcqBySection =
                mcqRepository.findMcqQuestionsByContestId(contestId)
                        .stream()
                        .collect(Collectors.groupingBy(McqQuestions::getSection));

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

        List<CodingQuestionDto> codingQuestions = codingRepository
                .findCodingQuestionsByContestId(contestId)
                .stream()
                .map(q -> {
                    List<String> inputParams = parseJsonArray(q.getInputParams());
                    List<String> inputType = parseJsonArray(q.getInputType());

                    List<TestcasesDTO> testcaseDTOs = testcaseRepo
                            .findByCodingQuestion_CodingQuestionId(q.getCodingQuestionId())
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
    }

    private List<String> parseJsonArray(String json) {
        try {
        	JsonNode root=objectMapper.readTree(json);
        	for(JsonNode node:root) {
        		if(node.isArray()) {
        			return objectMapper.convertValue(node,new TypeReference<List<String>>() {});
        		}
        	}
            return List.of();
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON: " + json, e);
        }
    }
    

        @Transactional
        public void handleCodingSubmissions(Submission submission, TestSubmissionRequest request) {

//            int totalCodingScore = 0;
//
//            if (request.getCodingSubmissions() != null) {
//                for (Map.Entry<String, SubmissionRequest> entry : request.getCodingSubmissions().entrySet()) {
//
//                    String questionId = entry.getKey();
//                    SubmissionRequest codingReq = entry.getValue();
//
//                    MapContestCoding mapContestCoding = codingRepository
//                            .findByQuestionIdAndContestId(questionId, request.getContestId())
//                            .orElseThrow(() -> new RuntimeException("Coding question not found: " + questionId));
//
//                    CodingQuestions question = mapContestCoding.getCodingQuestion();
//
//                    CodingSubmission codingSubmission = new CodingSubmission();
//                    codingSubmission.setCodingSubmissionId(customIdGenerator.generateCodingSubmissionId());
//                    codingSubmission.setSubmission(submission);
//                    codingSubmission.setCodingQuestion(question);
//                    codingSubmission.setCode(codingReq.getCode());
//                    codingSubmission.setSubmittedAt(LocalDateTime.now());
//                    codingSubmission.setCreatedAt(LocalDateTime.now());
//                    codingSubmission.setIsFinalAttempt(true);
//
//                    List<TestcaseResult> testcaseResults = new ArrayList<>();
//
//                    for (Testcases tc : question.getTestcases()) {
//                        TestcaseResult result = new TestcaseResult();
//                        result.setTestcaseResultId(customIdGenerator.generateTestcaseId());
//                        result.setCodingSubmission(codingSubmission);
//                        result.setTestcase(tc);
//                        result.setCreatedAt(LocalDateTime.now());
//                        result.setUpdatedAt(LocalDateTime.now());
//
//                        try {
//                            String output = dockerExecutorService.runTemporaryCode(
//                                    codingReq.getLanguageType(),
//                                    codingReq.getCode(),
//                                    tc.getInputValues()
//                            );
//
//                            boolean passed = output.trim().equals(tc.getExpectedOutput().trim());
//                            result.setTestcaseStatus(passed ? TestcaseResult.TestcaseResultStatus.PASSED
//                                                            : TestcaseResult.TestcaseResultStatus.FAILED);
//
//                        } catch (Exception e) {
//                        	//e.printStackTrace();
//                            result.setTestcaseStatus(TestcaseResult.TestcaseResultStatus.FAILED);
//                        }
//
//                        testcaseResults.add(result);
//                    }
//
//     
//                   int score = testcaseResults.stream()
//                            .filter(r -> r.getTestcaseStatus() == TestcaseResult.TestcaseResultStatus.PASSED)
//                            .mapToInt(r -> r.getTestcase().getWeightage())
//                            .sum();
//
//                    codingSubmission.setScore(score);
//
//                    boolean allPassed = testcaseResults.stream()
//                            .allMatch(r -> r.getTestcaseStatus() == TestcaseResult.TestcaseResultStatus.PASSED);
//
//                    boolean somePassed = testcaseResults.stream()
//                            .anyMatch(r -> r.getTestcaseStatus() == TestcaseResult.TestcaseResultStatus.PASSED);
//
//                    if (allPassed) {
//                        codingSubmission.setCodeStatus(CodingSubmission.CodingSubmissionStatus.SOLVED);
//                    } else if (somePassed) {
//                        codingSubmission.setCodeStatus(CodingSubmission.CodingSubmissionStatus.PARTIALLY_SOLVED);
//                    } else {
//                        codingSubmission.setCodeStatus(CodingSubmission.CodingSubmissionStatus.WRONG_ANSWER);
//                    }
//
//                    codingSubmission.setTestcaseResults(testcaseResults);
//                    testcaseResults.forEach(tcResult -> tcResult.setCodingSubmission(codingSubmission));
//
//                    totalCodingScore += score;
//
//                    codingSubmissionRepository.save(codingSubmission);
//                }
//            }
        	
        	List<CodingSubmission> latestSubmissions=codingSubmissionRepository
        			.findBySubmission_Participant_ParticipantIdAndSubmission_Contest_ContestId(request.getParticipantId(),request.getContestId());
        	int totalCodingScore=latestSubmissions.stream()
        			.mapToInt(CodingSubmission::getScore)
        			.sum();

            submission.setTotalCodingScore(totalCodingScore);
            submissionRepository.save(submission);
        }
        
        @Transactional
        public TestSubmissionResponse saveTestSubmission(TestSubmissionRequest request) {

           
        	Participant participant = participantRepository.findById(request.getParticipantId())
                    .orElseThrow(() -> new RuntimeException("Participant not found"));

            Contest contest = contestRepo.findById(request.getContestId())
                    .orElseThrow(() -> new RuntimeException("Contest not found"));
        	Submission submission = submissionRepository.findByParticipant_ParticipantIdAndContest_ContestId(
            		request.getParticipantId(),
            		request.getContestId()
            		).orElseGet(()->{
            				Submission newSubmission=new Submission();
            newSubmission.setSubmissionId(customIdGenerator.generateSubmissionId());
            newSubmission.setParticipant(participant);
            newSubmission.setContest(contest);
            return newSubmission;
            
            		});

            submission.setParticipant(participant);
            submission.setContest(contest);

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
                            .orElseThrow(() -> new RuntimeException("MCQ not found: " + mcqId));
                    McqQuestions question = mapContestMcq.getMcqQuestion();

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
            int codingScore=submission.getTotalCodingScore();
            int totalScore=totalMcqScore+codingScore;
            submission=submissionRepository.findById(submission.getSubmissionId()).orElseThrow(()->new RuntimeException("submission not found"));

            return TestSubmissionResponse.builder()
                    .submissionId(submission.getSubmissionId())
                    .submittedAt(submission.getSubmittedAt())
                    .totalMcqScore(totalMcqScore)
                    .totalCodingScore(codingScore)
                    .score(totalScore)
                    .status("SUCCESS")
                    .message("MCQ and Coding Submission successfully recorded and evaluated.")
                    .build();
        }
    }