package com.aaslin.cbt.participant.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aaslin.cbt.common.model.McqQuestion;

public interface MCQQuestionsRepository extends JpaRepository<McqQuestion,String>{

}
