package com.aaslin.cbt.developer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aaslin.cbt.common.model.Testcases;

public interface TestcaseRepository extends JpaRepository<Testcases,String>{
	
	List<Testcases> findByCodingQuestion_CodingQuestionIdAndTestcaseType(String codingQuestionId, Testcases.TestcaseType testcaseType);
	
	Optional<Testcases> findTopByOrderByTestcaseIdDesc();
	
	 List<Testcases> findByCodingQuestion_CodingQuestionId(String questionId);
	 
}
