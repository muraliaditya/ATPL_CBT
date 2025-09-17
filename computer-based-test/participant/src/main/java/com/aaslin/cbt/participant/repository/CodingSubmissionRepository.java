package com.aaslin.cbt.participant.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aaslin.cbt.common.model.CodingSubmission;

public interface CodingSubmissionRepository extends JpaRepository<CodingSubmission,String> { 

}
