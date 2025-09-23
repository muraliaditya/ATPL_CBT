package com.aaslin.cbt.participant.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aaslin.cbt.common.model.CodingQuestions;
import com.aaslin.cbt.common.model.CodingSubmission;
import com.aaslin.cbt.common.model.CodingSubmission.CodingSubmissionStatus;
import com.aaslin.cbt.common.model.LanguageType;
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

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CodingSubmissionService {

    private final SubmissionRepository submissionRepository;
    private final ParticipantRepository participantRepository;
    private final CodingQuestionsRepository codingQuestionsRepository;
    private final LanguageTypeRepository languageTypeRepository;
    private final CodingSubmissionRepository codingSubmissionRepository;
    private final TestcaseResultRepository testcaseResultRepository;

    
    @Transactional
    public void saveSubmission(SubmissionRequest request, SubmissionResponse response) {
        Participant participant = participantRepository.findById(request.getParticipantId())
                .orElseThrow(() -> new RuntimeException("Participant not found: " + request.getParticipantId()));

        Submission parent = new Submission();
        parent.setSubmissionId(generateSubmissionId());
      // parent.setSubmissionId(UUID.randomUUID().toString());
        parent.setParticipant(participant);
        parent.setSubmittedAt(LocalDateTime.now());
        parent.setTotalCodingScore(0);
        parent.setTotalMcqScore(0);
        parent.setTotalScore(0);
        parent.setCreatedAt(LocalDateTime.now());
        parent.setUpdatedAt(LocalDateTime.now());

        submissionRepository.save(parent);

        CodingQuestions question = codingQuestionsRepository.findById(request.getQuestionId())
                .orElseThrow(() -> new RuntimeException("Question not found: " + request.getQuestionId()));

        LanguageType lang = languageTypeRepository.findByLanguageType(request.getLanguageType())
                .orElseThrow(() -> new RuntimeException("Language type not found: " + request.getLanguageType()));

       CodingSubmission codingSubmission = new CodingSubmission();
     codingSubmission.setCodingSubmissionId(generateCodingSubmissionId());
       // codingSubmission.setCodingSubmissionId(UUID.randomUUID().toString());
        codingSubmission.setSubmission(parent);
        codingSubmission.setCodingQuestion(question);
        codingSubmission.setLanguageTypeId(lang);
        codingSubmission.setCode(request.getCode());
        codingSubmission.setScore(response.getScore());
        codingSubmission.setPublicTestcasesPassed(response.getPublicTestcasePassed());
        codingSubmission.setPrivateTestcasesPassed(response.getPrivateTestcasePassed());
        codingSubmission.setIsFinalAttempt(request.getIsFinalAttempt() != null ? request.getIsFinalAttempt() : false);
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
        result.setTestcaseResultId(UUID.randomUUID().toString());

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
    
    private String generateSubmissionId() {
    	List<String> ids=submissionRepository.findAllSubmissionIdsDesc();
    	if(ids.isEmpty()) return "SUB001";
    	String lastId=ids.get(0);
    	int num=Integer.parseInt(lastId.replace("SUB",""));
    	num++;
    	return String.format("SUB%03d", num);
    }
    
    private String generateCodingSubmissionId() {
    	List<String> ids=codingSubmissionRepository.findAllCodingSubmissionIdsDesc();
    	if(ids.isEmpty()) return "CSUB001";
    	String lastId=ids.get(0);
    	int num=Integer.parseInt(lastId.replace("CSUB",""));
    	num++;
    	return String.format("CSUB%03d", num);
    }
}