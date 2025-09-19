package com.aaslin.cbt.developer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.aaslin.cbt.common.model.CodingQuestions;
import com.aaslin.cbt.common.model.CodingQuestions.ApprovalStatus;

public interface CodingQuestionRepository extends JpaRepository<CodingQuestions, String> {

    // Default list (all approved questions, paginated)
    Page<CodingQuestions> findByApprovalStatus(ApprovalStatus APPROVED, Pageable pageable);

    // Search and Pagination
    Page<CodingQuestions> findByQuestionContainingIgnoreCaseAndApprovalStatus(String question, ApprovalStatus APPROVED, Pageable pageable);
}

