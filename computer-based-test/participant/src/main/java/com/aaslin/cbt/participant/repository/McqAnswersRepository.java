package com.aaslin.cbt.participant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.aaslin.cbt.common.model.McqAnswer;

public interface McqAnswersRepository extends JpaRepository<McqAnswer,String>{
	
	@Query("SELECT a.mcqAnswerId FROM McqAnswer a order by a.mcqAnswerId DESC")
	    List<String> findAllmcqAnswerIdsDesc();

}
