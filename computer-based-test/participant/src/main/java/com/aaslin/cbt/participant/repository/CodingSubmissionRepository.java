package com.aaslin.cbt.participant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.aaslin.cbt.common.model.CodingSubmission;

public interface CodingSubmissionRepository extends JpaRepository<CodingSubmission,String> { 
	List<CodingSubmission> findBySubmission_SubmissionId(String submissionId);
	
	@Query("SELECT  c.codingSubmissionId FROM CodingSubmission c ORDER BY c.codingSubmissionId DESC")
	List<String> findAllCodingSubmissionIdsDesc();

}
