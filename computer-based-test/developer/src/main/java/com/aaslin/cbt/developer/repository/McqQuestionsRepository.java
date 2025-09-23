package com.aaslin.cbt.developer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aaslin.cbt.common.model.McqQuestions;

public interface McqQuestionsRepository extends JpaRepository<McqQuestions, String> {

	Optional<McqQuestions> findTopByOrderByMcqQuestionIdDesc();
}
