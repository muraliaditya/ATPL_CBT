package com.aaslin.cbt.super_admin.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.aaslin.cbt.common.model.CodingSubmission;

public interface CodingSubmissionsRepository extends JpaRepository<CodingSubmission, String> {
    List<CodingSubmission> findBySubmission_SubmissionId(String submissionId);
}
