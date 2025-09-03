package com.aaslin.cbt.entity;

import jakarta.persistence.Entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "testcases")
public class TestCases {

    @Id
    @Column(name = "testcase_id", length = 100)
    private String testcaseId;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String input1;

    @Column(columnDefinition = "TEXT")
    private String input2;

    @Column(columnDefinition = "TEXT")
    private String output;

    @Enumerated(EnumType.STRING)
    @Column(name = "testcase_type")
    private TestcaseType testcaseType;

    @Column(name = "weightage", nullable = false)
    private int weightage = 3;

    @Column(name = "execution_time_limit")
    private BigDecimal executionTimeLimit;

    @Column(name = "memory_limit")
    private Long memoryLimit;

    @Column(name = "coding_question_id", length = 100)
    private String codingQuestionId;

    public enum TestcaseType {
        PUBLIC,
        PRIVATE
    }
}
