package com.aaslin.cbt.repo;

import com.aaslin.cbt.entity.MCQQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MCQQuestionRepository extends JpaRepository<MCQQuestion, String> {
}