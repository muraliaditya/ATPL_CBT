package com.aaslin.cbt.participant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aaslin.cbt.common.model.Testcases;

public interface TestCaseRepository extends JpaRepository<Testcases,String> {
	List<Testcases>  findByCodingQuestion_CodingQuestionId(String codingQuestionId);

}
