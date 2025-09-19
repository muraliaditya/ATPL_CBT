package com.aaslin.cbt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aaslin.cbt.entity.CodingQuestion;


@Repository
public interface CodingQuestionRepository extends JpaRepository<CodingQuestion,String>{
	
	List<CodingQuestion> findByContest_ContestId(String contestId);

	Optional<CodingQuestion> findById(String id);

	void deleteById(String id);

	@Query("SELECT c.codingQuestionId FROM CodingQuestion c")
	 List<String> findAllCodingIds();

}
