package com.aaslin.cbt.repo;

import com.aaslin.cbt.entity.MCQAnswers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MCQAnswersRepository extends JpaRepository<MCQAnswers, String> {
    List<MCQAnswers> findByMcqId(String mcqId);
}