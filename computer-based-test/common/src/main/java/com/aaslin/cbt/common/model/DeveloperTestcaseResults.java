package com.aaslin.cbt.common.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "developer_testcase_results_cbt")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class DeveloperTestcaseResults {
    @Id
    @Column(name = "developer_testcase_result_id", length = 50)
    private String developerTestcaseResultId;

    @ManyToOne
    @JoinColumn(name = "developer_coding_submission_id")
    private DeveloperCodingSubmissions developerCodingSubmission;

    @ManyToOne
    @JoinColumn(name = "testcase_id")
    private Testcases testcase;

    @Enumerated(EnumType.STRING)
    private DeveloperTestcaseStatus testcaseStatus;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public enum DeveloperTestcaseStatus {
    	PASSED, FAILED, TIMEOUT, MEMORY_LIMIT_EXCEEDED
  }
}