package com.aaslin.cbt.developer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aaslin.cbt.common.model.DeveloperTestcaseResults;

public interface DeveloperTestcaseResultsRepository extends JpaRepository<DeveloperTestcaseResults , String>{
	
	Optional<DeveloperTestcaseResults> findTopByOrderByDeveloperTestcaseResultIdDesc();
	Optional<DeveloperTestcaseResults>findByDeveloperCodingSubmission_DeveloperCodingSubmissionIdAndTestcase_TestcaseId(
			String developerCodingSubmissionId,String testcaseId);
}
