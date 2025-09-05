package com.aaslin.code_runner.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aaslin.code_runner.entity.CodingSubmission;

public interface CodingSubmissionRepository extends JpaRepository<CodingSubmission,String> {

}
