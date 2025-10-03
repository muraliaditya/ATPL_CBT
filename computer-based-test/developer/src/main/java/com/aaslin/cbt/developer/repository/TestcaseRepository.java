package com.aaslin.cbt.developer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aaslin.cbt.common.model.Testcase;

public interface TestcaseRepository extends JpaRepository<Testcase,String>{
	
	List<Testcase> findByCodingQuestion_CodingQuestionIdAndTestcaseType(String codingQuestionId, Testcase.TestcaseType testcaseType);
	
	Optional<Testcase> findTopByOrderByTestcaseIdDesc();
	
	 List<Testcase> findByCodingQuestion_CodingQuestionId(String questionId);
	 
}
