package com.aaslin.cbt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aaslin.cbt.entity.MCQQuestion;


@Repository
public interface MCQQuestionRepository extends JpaRepository<MCQQuestion,String> {
	List<MCQQuestion> findByContest_ContestId(String contestId);

	Optional<MCQQuestion> findById(String id);

	void deleteById(String id);
	
	@Query("SELECT m.mcqId FROM MCQQuestion m")
	 List<String> findAllMcqIds();




}


