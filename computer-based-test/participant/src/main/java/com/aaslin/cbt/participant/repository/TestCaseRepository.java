package com.aaslin.cbt.participant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aaslin.cbt.common.model.Testcase;

public interface TestCaseRepository extends JpaRepository<Testcase,String> {
	List<Testcase>  findByCodingQuestion_CodingQuestionId(String codingQuestionId);

	List<Testcase> findByCodingQuestion_CodingQuestionIdAndTestcaseType(String questionId, Testcase.TestcaseType type	);

}
