package com.aaslin.cbt.super_admin.service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aaslin.cbt.common.model.McqQuestions;
import com.aaslin.cbt.common.model.McqQuestions.ApprovalStatus;
import com.aaslin.cbt.common.model.User;
import com.aaslin.cbt.super_admin.dto.McqQuestionDTO;
import com.aaslin.cbt.super_admin.dto.McqRequestDTO;
import com.aaslin.cbt.super_admin.repository.McqQuestionRepository;
import com.aaslin.cbt.super_admin.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class McqRequestService {

    private final McqQuestionRepository mcqRepo;
    private final UsersRepository userRepo;

    public List<McqRequestDTO> getAllRequests(String username) {
        List<McqQuestions> mcqs;

        if (username != null && !username.isEmpty()) {
            mcqs = mcqRepo.findByCreatedByUsernameAndApprovalStatus(username, ApprovalStatus.PENDING);
        } else {
            mcqs = mcqRepo.findByApprovalStatus(ApprovalStatus.PENDING);
        }
        return mcqs.stream().map(mcq -> new McqRequestDTO(
                mcq.getMcqQuestionId(),
                mcq.getQuestionText(),
                mcq.getCreatedBy().getUsername(),
                mcq.getApprovalStatus()
        )).collect(Collectors.toList());
    }
    @Transactional
    public McqQuestionDTO updateApprovalStatus(String mcqId, String action, String approverId) {
    	
        McqQuestions mcq = mcqRepo.findById(mcqId)
                .orElseThrow(() -> new RuntimeException("MCQ not found"));
   
        User approver = userRepo.findById(approverId)
                .orElseThrow(() -> new RuntimeException("Approver not found"));

        if (mcq.getApprovalStatus() != McqQuestions.ApprovalStatus.PENDING) {
            throw new IllegalStateException("MCQ is already " + mcq.getApprovalStatus());
        }

        if ("approve".equalsIgnoreCase(action)) {
            mcq.setApprovalStatus(McqQuestions.ApprovalStatus.APPROVED);
        } else if ("reject".equalsIgnoreCase(action)) {
            mcq.setApprovalStatus(McqQuestions.ApprovalStatus.REJECTED);
        } else {
            throw new IllegalArgumentException("Invalid action: " + action);
        }

        mcq.setApprovedBy(approver);
        mcq.setUpdatedAt(LocalDateTime.now());
        mcq.setUpdatedBy(approver);

        McqQuestions savedMcq = mcqRepo.save(mcq);

        return new McqQuestionDTO(
            savedMcq.getMcqQuestionId(),
            savedMcq.getQuestionText(),
            savedMcq.getOption1(),
            savedMcq.getOption2(),
            savedMcq.getOption3(),
            savedMcq.getOption4(),
            savedMcq.getAnswerKey(),
            savedMcq.getSection().getSection(),
            savedMcq.getWeightage()
        );
    }
    @Transactional
    public McqQuestionDTO getMcqDetailsById(String mcqId) {
        McqQuestions mcq = mcqRepo.findById(mcqId)
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

    
}  
