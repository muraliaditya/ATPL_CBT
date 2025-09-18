package com.aaslin.cbt.common.model;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "testcase_results_cbt")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TestcaseResult {
    @Id
    @Column(name = "testcase_result_id", length = 50)
    private String testcaseResultId;

    @ManyToOne
    @JoinColumn(name = "testcase_id")
    private Testcases testcase;

    @ManyToOne
    @JoinColumn(name = "coding_submission_id")
    private CodingSubmission codingSubmission;

    @Enumerated(EnumType.STRING)
    private TestcaseResultStatus testcaseStatus;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

enum TestcaseResultStatus {
    PASSED, FAILED
}

