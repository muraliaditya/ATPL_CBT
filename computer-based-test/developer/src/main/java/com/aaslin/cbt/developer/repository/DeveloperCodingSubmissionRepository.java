package com.aaslin.cbt.developer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aaslin.cbt.common.model.DeveloperCodingSubmission;
import com.aaslin.cbt.common.model.DeveloperCodingSubmission.DeveloperCodingSubmissionStatus;
import com.aaslin.cbt.developer.Dto.TopDevelopersDto;

public interface DeveloperCodingSubmissionRepository extends JpaRepository<DeveloperCodingSubmission,String>{
	@Query("""
		       SELECT new com.aaslin.cbt.developer.Dto.TopDevelopersDto(
		           dcs.user.userId,
		           dcs.user.username,
		           COUNT(DISTINCT dcs.codingQuestion.codingQuestionId)
		       )
		       FROM DeveloperCodingSubmission dcs
		       WHERE dcs.codeStatus = :status
		       GROUP BY dcs.user.userId, dcs.user.username
		       ORDER BY COUNT(DISTINCT dcs.codingQuestion.codingQuestionId) DESC
		       """)
		List<TopDevelopersDto> findTopDevelopers(@Param("status") DeveloperCodingSubmissionStatus status);
	    Optional<DeveloperCodingSubmission> findTopByOrderByDeveloperCodingSubmissionIdDesc();
	    Optional<DeveloperCodingSubmission>findByUser_UserIdAndCodingQuestion_CodingQuestionId(String userId, String questionId);
}
