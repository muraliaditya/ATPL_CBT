package com.aaslin.cbt.participant.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aaslin.cbt.common.model.CodingQuestion;
import com.aaslin.cbt.common.model.MapContestCoding;

@Repository("participantCodingQuestionsRepository")
public interface CodingQuestionsRepository extends JpaRepository<MapContestCoding,String> {
	
	@Query("SELECT m.codingQuestion FROM MapContestCoding m where m.contest.contestId= :contestId")
	 List<CodingQuestion> findCodingQuestionsByContestId(@Param("contestId")String contestId);
	
	
	@Query("SELECT m FROM MapContestCoding m WHERE m.codingQuestion.id=:questionId AND m.contest.contestId= :contestId")
	Optional<MapContestCoding> findByQuestionIdAndContestId(@Param("questionId") String questionId,@Param("contestId") String contestId);
}
