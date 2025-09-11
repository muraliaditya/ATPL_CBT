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

@Entity
@Table(name = "testcases")
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
    @JoinColumn(name = "coding_question_id", insertable = false, updatable = false)
    private CodingQuestion codingQuestion;
    
    // Getters and Setters
    public String getTestcaseId() {
        return testcaseId;
    }

    public void setTestcaseId(String testcaseId) {
        this.testcaseId = testcaseId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInput1() {
        return input1;
    }

    public void setInput1(String input1) {
        this.input1 = input1;
    }

    public String getInput2() {
        return input2;
    }

    public void setInput2(String input2) {
        this.input2 = input2;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public TestcaseType getTestcaseType() {
        return testcaseType;
    }

    public void setTestcaseType(TestcaseType testcaseType) {
        this.testcaseType = testcaseType;
    }

    public int getWeightage() {
        return weightage;
    }

    public void setWeightage(int weightage) {
        this.weightage = weightage;
    }

    public BigDecimal getExecutionTimeLimit() {
        return executionTimeLimit;
    }

    public void setExecutionTimeLimit(BigDecimal executionTimeLimit) {
        this.executionTimeLimit = executionTimeLimit;
    }

    public Long getMemoryLimit() {
        return memoryLimit;
    }

    public void setMemoryLimit(Long memoryLimit) {
        this.memoryLimit = memoryLimit;
    }

    public String getCodingQuestionId() {
        return codingQuestionId;
    }

    public void setCodingQuestionId(String codingQuestionId) {
        this.codingQuestionId = codingQuestionId;
    }
    
    public CodingQuestion getCodingQuestion() {
        return codingQuestion;
    }

    public void setCodingQuestion(CodingQuestion codingQuestion) {
        this.codingQuestion = codingQuestion;
    }

    public enum TestcaseType {
        PUBLIC,
        PRIVATE
    }
}
