package com.aaslin.cbt.participant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aaslin.cbt.common.model.Testcases;

public interface TestCaseRepository extends JpaRepository<Testcases,String> {
	List<Testcases>  findByCodingQuestion_CodingQuestionId(String codingQuestionId);

	List<Testcases> findByCodingQuestion_CodingQuestionIdAndTestcaseType(String questionId, Testcases.TestcaseType type	);

}
