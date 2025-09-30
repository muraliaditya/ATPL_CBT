package com.aaslin.cbt.participant.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aaslin.cbt.common.model.McqQuestions;

public interface MCQQuestionsRepository extends JpaRepository<McqQuestions,String>{

}
