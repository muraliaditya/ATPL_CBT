package com.cbt.mcq_test.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cbt.mcq_test.entity.McqQuestion;
import com.cbt.mcq_test.entity.McqQuestion.Section;

public interface McqQuestionRepository extends JpaRepository<McqQuestion,String> {
	
	    List<McqQuestion> findBySection(Section section);

}
