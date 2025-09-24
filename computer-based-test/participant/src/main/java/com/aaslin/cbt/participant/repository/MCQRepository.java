package com.aaslin.cbt.participant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aaslin.cbt.common.model.McqQuestions;
import com.aaslin.cbt.participant.dto.MCQSection;

public interface MCQRepository extends JpaRepository<McqQuestions,String>{

	    @Query("SELECT new com.example.MCQSection(m.section, m.questions) FROM MCQQuestion m WHERE m.contest.contestId = :contestId")
	    List<MCQSection> findSectionsByContest(@Param("contestId") String contestId);

}
