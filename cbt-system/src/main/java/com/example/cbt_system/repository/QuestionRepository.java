package com.example.cbt_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.cbt_system.entity.Question;

public interface QuestionRepository extends JpaRepository<Question,Long> {
	
	@Query(value="select * from question where status='approved' order by rand() limit 1",nativeQuery=true)
	Question findRandomApprovedQuestion();

	List<Question> findByStatus(String string);

}
