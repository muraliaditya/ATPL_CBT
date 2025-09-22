package com.aaslin.cbt.developer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.aaslin.cbt.common.model.CodingQuestions;
import com.aaslin.cbt.common.model.CodingQuestions.ApprovalStatus;

public interface CodingQuestionRepository extends JpaRepository<CodingQuestions, String> {

    // Default list (all approved questions, paginated)
    Page<CodingQuestions> findByApprovalStatus(ApprovalStatus APPROVED, Pageable pageable);

    // Search and Pagination
    Page<CodingQuestions> findByQuestionContainingIgnoreCaseAndApprovalStatus(String question, ApprovalStatus status, Pageable pageable);
    
    //Fetch top 10 recently added questions
    List<CodingQuestions> findTop10ByApprovalStatusOrderByCreatedAtDesc(CodingQuestions.ApprovalStatus status);
    
    //Fetch questions by Id
    Optional<CodingQuestions> findByCodingQuestionIdAndApprovalStatus(String codingQuestionId, CodingQuestions.ApprovalStatus status);
    
    Optional<CodingQuestions> findTopByOrderByCodingQuestionIdDesc();
    
    Long countByApprovalStatus(ApprovalStatus approvalStatus);
}

