package com.aaslin.coding_cbt.Entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="coding_questions_cbt")
public class CodingQuestion {

    @Id
    @Column(name="pk_coding_question_id",nullable=false)
    private String id;

    @Column(nullable=false)
    private String question;

    @Column(length=2000)
    private String description;

    private String difficulty;
    private String outputFormat;

	@Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus = ApprovalStatus.PENDING;

    private LocalDateTime createdAt = LocalDateTime.now();
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
    private String approvedBy;

    @Column(length=5000)
    private String javaBoilerplateCode;

    @Column(length=5000)
    private String pythonBoilerplateCode;

    private Double executionTimeLimit;
    private Integer memoryLimit;
    private String inputType;
    private String inputParams;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<CodingTestCase> testcases;

    public enum ApprovalStatus {
        PENDING, APPROVED, REJECTED
    }

    @PrePersist
    public void prePersist() {
        if(this.id == null) {
            long number = System.currentTimeMillis() % 1000;
            this.id = "Q" + String.format("%03d", number);
        }
    }
}