package com.aaslin.cbt.developer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aaslin.cbt.common.model.DeveloperCodingSubmissions;
import com.aaslin.cbt.common.model.DeveloperCodingSubmissions.DeveloperCodingSubmissionStatus;
import com.aaslin.cbt.developer.Dto.TopDevelopersDto;

public interface DeveloperCodingSubmissionRepository extends JpaRepository<DeveloperCodingSubmissions,String>{
	@Query("""
		       SELECT new com.aaslin.cbt.developer.Dto.TopDevelopersDto(
		           dcs.user.userId,
		           dcs.user.username,
		           COUNT(DISTINCT dcs.codingQuestion.codingQuestionId)
		       )
		       FROM DeveloperCodingSubmissions dcs
		       WHERE dcs.codeStatus = :status
		       GROUP BY dcs.user.userId, dcs.user.username
		       ORDER BY COUNT(DISTINCT dcs.codingQuestion.codingQuestionId) DESC
		       """)
		List<TopDevelopersDto> findTopDevelopers(@Param("status") DeveloperCodingSubmissionStatus status);
}
