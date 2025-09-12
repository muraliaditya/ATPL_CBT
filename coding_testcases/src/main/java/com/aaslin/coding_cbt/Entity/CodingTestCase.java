package com.aaslin.coding_cbt.Entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="coding_testcases_cbt")
public class CodingTestCase {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="question_id", nullable=false)
    @JsonBackReference
    private CodingQuestion question;

    @Column(length=2000, nullable=false)
    private String description;

    @Column(length=2000, nullable=false)
    @JsonProperty("inputValues")
    private String inputValues;

    @Column(length=2000, nullable=false)
    @JsonProperty("outputValues")
    private String output;

    @Enumerated(EnumType.STRING)
    private TestCaseType type = TestCaseType.HIDDEN;

    private Integer weightage = 0;
    private LocalDateTime createdAt = LocalDateTime.now();
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;

    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus = ApprovalStatus.PENDING;

    public enum TestCaseType {
        SAMPLE, HIDDEN
    }

    public enum ApprovalStatus {
        PENDING, APPROVED, REJECTED
    }

    @PrePersist
    public void prePersist() {
        if (this.id == null) {
            this.id = "TC" + UUID.randomUUID().toString().replace("-", "").substring(0, 6).toUpperCase();
        }
    }

}