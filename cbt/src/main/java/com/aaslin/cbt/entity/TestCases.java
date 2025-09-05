package com.aaslin.cbt.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "testcases")
@Getter
@Setter
public class TestCases {

    @Id
    @Column(name = "testcase_id", length = 100)
    private String testcaseId;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "input1", columnDefinition = "TEXT")
    private String input1;

    @Column(name = "input2", columnDefinition = "TEXT")
    private String input2;

    @Column(name = "output", columnDefinition = "TEXT")
    private String output;

    @Enumerated(EnumType.STRING)
    @Column(name = "testcase_type")
    private TestcaseType testcaseType;

    @Column(name = "weightage")
    private int weightage = 3;

    @Column(name = "execution_time_limit", precision = 10, scale = 2)
    private BigDecimal executionTimeLimit;

    @Column(name = "memory_limit")
    private Long memoryLimit;

    @Column(name = "coding_question_id", length = 100)
    private String codingQuestionId;
    
    // Relationships
    @ManyToOne
    @JoinColumn(name = "coding_questi"
    		+ ""
    		+ "on_id", insertable = false, updatable = false)
    private CodingQuestion codingQuestion;
    
    public TestcaseType getTestcaseType() {
        return testcaseType;
    }

    public void setTestcaseType(TestcaseType testcaseType) {
        this.testcaseType = testcaseType;
    }

    public enum TestcaseType {
        PUBLIC,
        PRIVATE
    }
}
