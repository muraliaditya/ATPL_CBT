package com.aaslin.cbt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aaslin.cbt.entity.MCQQuestion;

public interface MCQQuestionRepository extends JpaRepository<MCQQuestion,Long> {
	List<MCQQuestion> findByContest_Contestid(String contestid);


}


