package com.aaslin.coding_cbt.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aaslin.coding_cbt.Entity.CodingQuestion;

public interface CodingQuestionRepository extends JpaRepository<CodingQuestion, String> {
}
