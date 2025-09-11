package com.aaslin.coding_cbt.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "coding_questions_cbt")
public class CodingQuestion {

    @Id
    @Column(name = "pk_coding_question_id")
    private String id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String question;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    private String outputFormat;

    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus = ApprovalStatus.PENDING;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    private String createdBy;
    private String updatedBy;
    private String approvedBy;

    @Column(columnDefinition = "TEXT")
    private String javaBoilerplateCode;

    @Column(columnDefinition = "TEXT")
    private String pythonBoilerplateCode;

    private BigDecimal executionTimeLimit = BigDecimal.valueOf(1.0);

    private Long memoryLimit;

    @Column(columnDefinition = "JSON")
    private String inputType;   

    @Column(columnDefinition = "JSON")
    private String inputParams; 

    public enum Difficulty {
        EASY, MEDIUM, HARD
    }

    public enum ApprovalStatus {
        PENDING, APPROVED, REJECTED
    }

}
