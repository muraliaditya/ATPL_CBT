package com.aaslin.cbt.developer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aaslin.cbt.common.model.McqQuestion;

public interface McqQuestionsRepository extends JpaRepository<McqQuestion, String> {

	Optional<McqQuestion> findTopByOrderByMcqQuestionIdDesc();
}
