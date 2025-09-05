package com.aaslin.code_runner.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aaslin.code_runner.entity.CodingTestcase;

public interface CodingTestcaseRepository extends JpaRepository<CodingTestcase,String> {
	
   List<CodingTestcase> findByCodingQuestionId(String codingQuestionId);
}
