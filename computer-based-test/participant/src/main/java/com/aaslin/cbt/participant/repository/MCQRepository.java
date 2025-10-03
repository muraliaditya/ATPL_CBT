package com.aaslin.cbt.participant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aaslin.cbt.common.model.MapContestMcq;
import com.aaslin.cbt.common.model.McqQuestion;
import com.aaslin.cbt.participant.dto.MCQSection;

public interface MCQRepository extends JpaRepository<MapContestMcq,String>{

	   @Query("SELECT m.mcqQuestion FROM MapContestMcq m WHERE m.contest.contestId= :contestId")
	    List<McqQuestion> findMcqQuestionsByContestId(@Param("contestId") String contestId);

}
