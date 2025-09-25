package com.aaslin.cbt.developer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.aaslin.cbt.common.model.CodingQuestions;

public interface CodingQuestionRepository extends JpaRepository<CodingQuestions, String> {

    // Default list (all approved questions, paginated)
    Page<CodingQuestions> findByApprovalStatus(CodingQuestions.ApprovalStatus APPROVED, Pageable pageable);

    // Search and Pagination
    Page<CodingQuestions> findByQuestionContainingIgnoreCaseAndApprovalStatus(String question, CodingQuestions.ApprovalStatus status, Pageable pageable);
    
    //Difficulty search
    Page<CodingQuestions> findByDifficultyAndApprovalStatus(CodingQuestions.Difficulty difficulty, CodingQuestions.ApprovalStatus approvalStatus,Pageable pageable);

    //difficulty and question search
    Page<CodingQuestions> findByQuestionContainingIgnoreCaseAndDifficultyAndApprovalStatus(String question,CodingQuestions.Difficulty difficulty,CodingQuestions.ApprovalStatus approvalStatus,Pageable pageable);
    
    //Fetch top 10 recently added questions
    //List<CodingQuestions> findTop10ByApprovalStatusOrderByCreatedAtDesc(CodingQuestions.ApprovalStatus status);
    
    // dynamic limit using Pageable to fetch recently added questions
    List<CodingQuestions> findByApprovalStatusOrderByCreatedAtDesc(CodingQuestions.ApprovalStatus approvalStatus,Pageable pageable);
    
    //Fetch questions by Id
    Optional<CodingQuestions> findByCodingQuestionIdAndApprovalStatus(String codingQuestionId, CodingQuestions.ApprovalStatus status);
    
    Optional<CodingQuestions> findTopByOrderByCodingQuestionIdDesc();
    
    Long countByApprovalStatus(CodingQuestions.ApprovalStatus approvalStatus);
}

