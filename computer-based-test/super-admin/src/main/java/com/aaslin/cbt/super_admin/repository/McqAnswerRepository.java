package com.aaslin.cbt.super_admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aaslin.cbt.common.model.McqAnswer;

public interface McqAnswerRepository extends JpaRepository<McqAnswer,String> {
	List<McqAnswer>findBySubmission_SubmissionId(String submissionId);
}
