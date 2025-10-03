package com.aaslin.cbt.super_admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aaslin.cbt.common.model.McqQuestion;
import com.aaslin.cbt.common.model.McqQuestion.ApprovalStatus;
import com.aaslin.cbt.common.model.Section;


public interface McqQuestionRepository extends JpaRepository<McqQuestion,String> {
	
	    @Query(value = "SELECT * FROM mcq_questions_cbt WHERE section_id = :sectionId AND is_active=false  ORDER BY RAND() LIMIT :count", nativeQuery = true)
	    List<McqQuestion> findRandomBySection(@Param("sectionId") String sectionId, @Param("count") int count);

	    @Query(value = "SELECT * FROM mcq_questions_cbt WHERE section_id = :sectionId AND mcq_question_id <> :currentQuestionId AND is_active=false ORDER BY RAND() LIMIT 1", nativeQuery = true)
	    McqQuestion findRandomOtherBySection(@Param("sectionId") String sectionId, @Param("currentQuestionId") String currentQuestionId);

    
	    List<McqQuestion> findBySectionAndIsActiveFalse(Section section);
	    List<McqQuestion> findAllByIsActiveFalse();
	    List<McqQuestion> findBySection(Section section);
	    List<McqQuestion> findByApprovalStatus(ApprovalStatus approvalStatus);
	    List<McqQuestion> findByCreatedByUsernameAndApprovalStatus(String username, ApprovalStatus approvalStatus);

	   

    @Query(value = "SELECT MAX(CAST(SUBSTRING(mcq_question_id, 4) AS UNSIGNED)) FROM mcq_questions_cbt", nativeQuery = true)
    int findMaxIdNumber();    

}

