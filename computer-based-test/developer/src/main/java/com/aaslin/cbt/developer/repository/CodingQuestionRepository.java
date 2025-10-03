package com.aaslin.cbt.developer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.aaslin.cbt.common.model.CodingQuestion;

public interface CodingQuestionRepository extends JpaRepository<CodingQuestion, String> {

    // Default list (all approved questions, paginated)
    Page<CodingQuestion> findByApprovalStatus(CodingQuestion.ApprovalStatus APPROVED, Pageable pageable);

    // Search and Pagination
    Page<CodingQuestion> findByQuestionContainingIgnoreCaseAndApprovalStatus(String question, CodingQuestion.ApprovalStatus status, Pageable pageable);
    
    //Difficulty search
    Page<CodingQuestion> findByDifficultyAndApprovalStatus(CodingQuestion.Difficulty difficulty, CodingQuestion.ApprovalStatus approvalStatus,Pageable pageable);

    //difficulty and question search
    Page<CodingQuestion> findByQuestionContainingIgnoreCaseAndDifficultyAndApprovalStatus(String question,CodingQuestion.Difficulty difficulty,CodingQuestion.ApprovalStatus approvalStatus,Pageable pageable);
    
    //Fetch top 10 recently added questions
    //List<CodingQuestions> findTop10ByApprovalStatusOrderByCreatedAtDesc(CodingQuestions.ApprovalStatus status);
    
    // dynamic limit using Pageable to fetch recently added questions
    List<CodingQuestion> findByApprovalStatusOrderByCreatedAtDesc(CodingQuestion.ApprovalStatus approvalStatus,Pageable pageable);
    
    //Fetch questions by Id
    Optional<CodingQuestion> findByCodingQuestionIdAndApprovalStatus(String codingQuestionId, CodingQuestion.ApprovalStatus status);
    
    Optional<CodingQuestion> findTopByOrderByCodingQuestionIdDesc();
    
    Long countByApprovalStatus(CodingQuestion.ApprovalStatus approvalStatus);
}

