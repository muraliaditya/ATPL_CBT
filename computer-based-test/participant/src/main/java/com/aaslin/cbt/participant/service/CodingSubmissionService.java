package com.aaslin.cbt.participant.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aaslin.cbt.common.model.CodingSubmission;
import com.aaslin.cbt.common.model.CodingSubmission.CodingSubmissionStatus;
import com.aaslin.cbt.common.model.LanguageType;
import com.aaslin.cbt.common.model.MapContestCoding;
import com.aaslin.cbt.common.model.Participant;
import com.aaslin.cbt.common.model.Submission;
import com.aaslin.cbt.common.model.TestcaseResult;
import com.aaslin.cbt.common.model.Testcases;
import com.aaslin.cbt.participant.dto.SubmissionRequest;
import com.aaslin.cbt.participant.dto.SubmissionResponse;
import com.aaslin.cbt.participant.dto.TestcaseResultResponse;
import com.aaslin.cbt.participant.repository.CodingQuestionsRepository;
import com.aaslin.cbt.participant.repository.CodingSubmissionRepository;
import com.aaslin.cbt.participant.repository.LanguageTypeRepository;
import com.aaslin.cbt.participant.repository.ParticipantRepository;
import com.aaslin.cbt.participant.repository.SubmissionRepository;
import com.aaslin.cbt.participant.repository.TestcaseResultRepository;
import com.aaslin.cbt.participant.util.CustomIdGenerator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CodingSubmissionService {

    private static final int MAX_ATTEMPTS = 50;
	private final SubmissionRepository submissionRepository;
    private final ParticipantRepository participantRepository;
    private final CodingQuestionsRepository codingQuestionsRepository;
    private final LanguageTypeRepository languageTypeRepository;
    private final CodingSubmissionRepository codingSubmissionRepository;
    private final TestcaseResultRepository testcaseResultRepository;
    
    @Autowired
    private CustomIdGenerator customIdGenerator;

    
    @Transactional
    public void saveCodingSubmission(SubmissionRequest request, SubmissionResponse response) {
        Participant participant = participantRepository.findById(request.getParticipantId())
                .orElseThrow(() -> new RuntimeException("Participant not found: " + request.getParticipantId()));

        MapContestCoding question = codingQuestionsRepository.findByQuestionIdAndContestId(request.getQuestionId(),request.getContestId())
                .orElseThrow(() -> new RuntimeException("Question not found: " + request.getQuestionId()));

        LanguageType lang = languageTypeRepository.findByLanguageType(request.getLanguageType())
                .orElseThrow(() -> new RuntimeException("Language type not found: " + request.getLanguageType()));

        int attemptNumber = codingSubmissionRepository
                .countBySubmission_Participant_ParticipantIdAndCodingQuestion_CodingQuestionId(
                        participant.getParticipantId(),
                        question.getCodingQuestion().getCodingQuestionId()
                );

        boolean finalAttempt = (attemptNumber + 1 == MAX_ATTEMPTS);

        CodingSubmission latestSubmission = codingSubmissionRepository
                .findTopBySubmission_Participant_ParticipantIdAndCodingQuestion_CodingQuestionIdOrderByCreatedAtDesc(
                        participant.getParticipantId(),
                        question.getCodingQuestion().getCodingQuestionId()
                );

        String codingSubmissionId;
        Submission parentSubmission;

        if (latestSubmission == null || finalAttempt) {
            codingSubmissionId = customIdGenerator.generateCodingSubmissionId();

            parentSubmission = new Submission();
            parentSubmission.setSubmissionId(customIdGenerator.generateSubmissionId());
            parentSubmission.setParticipant(participant);
            parentSubmission.setSubmittedAt(LocalDateTime.now());
            parentSubmission.setTotalCodingScore(0);
            parentSubmission.setTotalMcqScore(0);
            parentSubmission.setTotalScore(0);
            parentSubmission.setCreatedAt(LocalDateTime.now());
            parentSubmission.setUpdatedAt(LocalDateTime.now());

            submissionRepository.save(parentSubmission);
        } else {
            codingSubmissionId = latestSubmission.getCodingSubmissionId();
            parentSubmission = latestSubmission.getSubmission();
        }

        CodingSubmission codingSubmission = new CodingSubmission();
        codingSubmission.setCodingSubmissionId(codingSubmissionId);
        codingSubmission.setSubmission(parentSubmission);
        codingSubmission.setCodingQuestion(question.getCodingQuestion());
        codingSubmission.setLanguageTypeId(lang);
        codingSubmission.setCode(request.getCode());
        codingSubmission.setScore(response.getScore());
        codingSubmission.setPublicTestcasesPassed(response.getPublicTestcasePassed());
        codingSubmission.setPrivateTestcasesPassed(response.getPrivateTestcasePassed());
        codingSubmission.setIsFinalAttempt(finalAttempt);
        codingSubmission.setSubmittedAt(LocalDateTime.now());
        codingSubmission.setCreatedAt(LocalDateTime.now());
        codingSubmission.setUpdatedAt(LocalDateTime.now());
        codingSubmission.setCodeStatus(CodingSubmissionStatus.valueOf(response.getCodeStatus().toUpperCase()));

        codingSubmissionRepository.save(codingSubmission);
        for (TestcaseResultResponse tc : response.getPublicTestcaseResults()) {
            saveTestcaseResult(codingSubmission, tc, true);
        }

        for (TestcaseResultResponse tc : response.getPrivateTestcaseResults()) {
            saveTestcaseResult(codingSubmission, tc, false);
        }
    }

    private void saveTestcaseResult(CodingSubmission codingSubmission, TestcaseResultResponse tc, boolean isPublic) {

        TestcaseResult result = new TestcaseResult();
        result.setTestcaseResultId(customIdGenerator.generateTestcaseId());

        result.setCodingSubmission(codingSubmission);

        Testcases testcase = new Testcases();
        testcase.setTestcaseId(tc.getTestcaseId());
        result.setTestcase(testcase);

        result.setTestcaseStatus(tc.getStatus().equalsIgnoreCase("PASSED") 
                ? TestcaseResult.TestcaseResultStatus.PASSED 
                : TestcaseResult.TestcaseResultStatus.FAILED);

        result.setCreatedAt(LocalDateTime.now());
        result.setUpdatedAt(LocalDateTime.now());

        testcaseResultRepository.save(result);
    }


   
}