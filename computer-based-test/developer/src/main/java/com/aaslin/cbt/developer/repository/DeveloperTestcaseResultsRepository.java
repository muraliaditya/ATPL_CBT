package com.aaslin.cbt.developer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aaslin.cbt.common.model.DeveloperTestcaseResult;

public interface DeveloperTestcaseResultsRepository extends JpaRepository<DeveloperTestcaseResult , String>{
	
	Optional<DeveloperTestcaseResult> findTopByOrderByDeveloperTestcaseResultIdDesc();
	Optional<DeveloperTestcaseResult>findByDeveloperCodingSubmission_DeveloperCodingSubmissionIdAndTestcase_TestcaseId(
			String developerCodingSubmissionId,String testcaseId);
}
