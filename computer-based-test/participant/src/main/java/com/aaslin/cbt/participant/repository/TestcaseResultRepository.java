package com.aaslin.cbt.participant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aaslin.cbt.common.model.TestcaseResult;

public interface TestcaseResultRepository extends JpaRepository<TestcaseResult,String> {
	
	List<TestcaseResult> findByCodingSubmission_CodingSubmissionId(String codingSubmissionId);

}
