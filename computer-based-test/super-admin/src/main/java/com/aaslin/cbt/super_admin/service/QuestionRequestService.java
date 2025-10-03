package com.aaslin.cbt.super_admin.service;
import org.springframework.stereotype.Service;

import com.aaslin.cbt.super_admin.dto.CodingQuestionRequest;
import com.aaslin.cbt.super_admin.dto.McqQuestionDTO;
import com.aaslin.cbt.super_admin.dto.QuestionRequestDTO;
import com.aaslin.cbt.super_admin.repository.CodingQuestionsRepository;
import com.aaslin.cbt.super_admin.repository.McqQuestionRepository;
import com.aaslin.cbt.super_admin.repository.UsersRepository;

import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;

import com.aaslin.cbt.common.model.CodingQuestion;
import com.aaslin.cbt.common.model.McqQuestion;
import com.aaslin.cbt.common.model.McqQuestion.ApprovalStatus;
import com.aaslin.cbt.common.model.User;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class QuestionRequestService {

    private final McqQuestionRepository mcqRepo;
    private final CodingQuestionsRepository codingRepo;
    private final UsersRepository userRepo;

    public List<QuestionRequestDTO> getAllRequests(String username) {
        List<QuestionRequestDTO> mcqRequests = (username != null && !username.isEmpty())
            ? mcqRepo.findByCreatedByUsernameAndApprovalStatus(username, McqQuestion.ApprovalStatus.PENDING)
                .stream()
                .map(mcq -> new QuestionRequestDTO(
                    mcq.getMcqQuestionId(),
                    mcq.getQuestionText(),
                    mcq.getCreatedBy().getUsername(),
                    "MCQ",
                    mcq.getApprovalStatus().name(),
                    mcq.getCreatedBy().getUserId()))
                .collect(Collectors.toList())
            : mcqRepo.findByApprovalStatus(McqQuestion.ApprovalStatus.PENDING)
                .stream()
                .map(mcq -> new QuestionRequestDTO(
                    mcq.getMcqQuestionId(),
                    mcq.getQuestionText(),
                    mcq.getCreatedBy().getUsername(),
                    "MCQ",
                    mcq.getCreatedBy().getUserId(),
                    mcq.getApprovalStatus().name()))
                .collect(Collectors.toList());

        List<QuestionRequestDTO> codingRequests = (username != null && !username.isEmpty())
            ? codingRepo.findByCreatedByUsernameAndApprovalStatus(username, CodingQuestion.ApprovalStatus.PENDING)
                .stream()
                .map(cq -> new QuestionRequestDTO(
                    cq.getCodingQuestionId(),
                    cq.getQuestion(),
                    cq.getCreatedBy().getUsername(),
                    "CODING",
                    cq.getCreatedBy().getUserId(),
                    cq.getApprovalStatus().name()))
                .collect(Collectors.toList())
            : codingRepo.findByApprovalStatus(CodingQuestion.ApprovalStatus.PENDING)
                .stream()
                .map(cq -> new QuestionRequestDTO(
                    cq.getCodingQuestionId(),
                    cq.getQuestion(),
                    cq.getCreatedBy().getUsername(),
                    "CODING",
                    cq.getCreatedBy().getUserId(),
                    cq.getApprovalStatus().name()))
                .collect(Collectors.toList());

        mcqRequests.addAll(codingRequests);
        return mcqRequests;
    }

    @Transactional
    public Object updateApprovalStatus(String questionId, String action, String approverId, String type) {
        User approver = userRepo.findById(approverId)
            .orElseThrow(() -> new RuntimeException("Approver not found"));

        if ("MCQ".equalsIgnoreCase(type)) {
            McqQuestion mcq = mcqRepo.findById(questionId)
                .orElseThrow(() -> new RuntimeException("MCQ not found"));

            if (mcq.getApprovalStatus() != ApprovalStatus.PENDING)
                throw new IllegalStateException("MCQ is already " + mcq.getApprovalStatus());

            mcq.setApprovalStatus("approve".equalsIgnoreCase(action) ? ApprovalStatus.APPROVED : ApprovalStatus.REJECTED);
            mcq.setApprovedBy(approver);
            mcq.setUpdatedAt(LocalDateTime.now());
            mcq.setUpdatedBy(approver);
            mcqRepo.save(mcq);

            return "Updated SuccesFully";
        } else if ("CODING".equalsIgnoreCase(type)) {
            CodingQuestion cq = codingRepo.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Coding question not found"));

            if (cq.getApprovalStatus()!=CodingQuestion.ApprovalStatus.PENDING)
                throw new IllegalStateException("Coding question is already " + cq.getApprovalStatus());

            cq.setApprovalStatus("approve".equalsIgnoreCase(action) ? CodingQuestion.ApprovalStatus.APPROVED : CodingQuestion.ApprovalStatus.REJECTED);
            cq.setApprovedBy(approver);
            cq.setUpdatedAt(LocalDateTime.now());
            cq.setUpdatedBy(approver);
            codingRepo.save(cq);

            return "Updated SuccessFully";
        } else {
            throw new IllegalArgumentException("Invalid type: " + type);
        }
    }
    @Transactional
    public McqQuestionDTO getMcqDetailsById(String mcqId) {
        McqQuestion mcq = mcqRepo.findById(mcqId)
                .orElseThrow(() -> new RuntimeException("MCQ not found"));
        return new McqQuestionDTO(
            mcq.getMcqQuestionId(),
            mcq.getQuestionText(),
            mcq.getOption1(),
            mcq.getOption2(),
            mcq.getOption3(),
            mcq.getOption4(),
            mcq.getAnswerKey(),
            mcq.getSection().getSection(),
            mcq.getWeightage()
        );
    }
    public CodingQuestionRequest getCodingDetailsById(String codingQuestionId) {
    	CodingQuestion coding = codingRepo.findById(codingQuestionId).orElseThrow(() -> new RuntimeException("Coding Id not Found"));
    	return new CodingQuestionRequest(
    			);
    }
}
