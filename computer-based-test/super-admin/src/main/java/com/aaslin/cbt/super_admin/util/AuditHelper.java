package com.aaslin.cbt.super_admin.util;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.aaslin.cbt.common.model.CodingQuestions;
import com.aaslin.cbt.common.model.Contest;
import com.aaslin.cbt.common.model.MapContestCoding;
import com.aaslin.cbt.common.model.MapContestMcq;
import com.aaslin.cbt.common.model.McqQuestions;
import com.aaslin.cbt.common.model.Testcases;
import com.aaslin.cbt.common.model.User;
import com.aaslin.cbt.super_admin.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuditHelper {

    private final SecurityUtils securityUtils;
    private final UsersRepository userRepository;

    public User getCurrentUser() {
        String currentUser = securityUtils.getCurrentUserId();
        return userRepository.findByUsername(currentUser).orElse(null);
    }

    public void applyAuditForCodingQuestion(CodingQuestions cq) {
        User user = getCurrentUser();
        cq.setUpdatedBy(user);
        cq.setUpdatedAt(LocalDateTime.now());

        if (cq.getCreatedBy() == null) {
            cq.setCreatedBy(user);
            cq.setCreatedAt(LocalDateTime.now());
        }

        if (user != null && "SUPER_ADMIN".equals(user.getRole().getRole())) {
            cq.setApprovalStatus(CodingQuestions.ApprovalStatus.APPROVED);
            cq.setApprovedBy(user);
        } else {
            cq.setApprovalStatus(CodingQuestions.ApprovalStatus.PENDING);
        }
    }

    
    public void applyAuditForMcqQuestion(McqQuestions mcq) {
        User user = getCurrentUser();
        mcq.setUpdatedBy(user);
        mcq.setUpdatedAt(LocalDateTime.now());

        if (mcq.getCreatedBy() == null) {
            mcq.setCreatedBy(user);
            mcq.setCreatedAt(LocalDateTime.now());
        }

        if (user != null && "SUPER_ADMIN".equals(user.getRole().getRole())) {
            mcq.setApprovalStatus(McqQuestions.ApprovalStatus.APPROVED);
            mcq.setApprovedBy(user);
        } else {
            mcq.setApprovalStatus(McqQuestions .ApprovalStatus.PENDING);
        }
    }
    
    public void applyAuditForMapContestMcq(MapContestMcq mapMcq) {
        User user = getCurrentUser();
        mapMcq.setUpdatedBy(user);
        mapMcq.setUpdatedAt(LocalDateTime.now());

        if (mapMcq.getCreatedBy() == null) {
            mapMcq.setCreatedBy(user);
            mapMcq.setCreatedAt(LocalDateTime.now());
        }
    }

    public void applyAuditForMapContestCoding(MapContestCoding mapCoding) {
        User user = getCurrentUser();
        mapCoding.setUpdatedBy(user);
        mapCoding.setUpdatedAt(LocalDateTime.now());

        if (mapCoding.getCreatedBy() == null) {
            mapCoding.setCreatedBy(user);
            mapCoding.setCreatedAt(LocalDateTime.now());
        }
    }
    
    public void applyAuditForContest(Contest contest) {
        User user = getCurrentUser();
        contest.setUpdatedBy(user);
        contest.setUpdatedAt(LocalDateTime.now());

        if (contest.getCreatedBy() == null) {
            contest.setCreatedBy(user);
            contest.setCreatedAt(LocalDateTime.now());
        }
    }
    
    public void applyAuditForTestcase(Testcases testcase) {
        User user = getCurrentUser();
        testcase.setUpdatedBy(user);
        testcase.setUpdatedAt(LocalDateTime.now());

        if (testcase.getCreatedBy() == null) {
            testcase.setCreatedBy(user);
            testcase.setCreatedAt(LocalDateTime.now());
        }
    }
}