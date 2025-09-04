package com.aaslin.testcase.testcases.model;

import java.math.BigDecimal;
import jakarta.persistence.*;

@Entity
@Table(name="testcases")
public class Testcase {

    @Id
    @Column(name="testcase_id")
    private String testcaseId; 

    @Column(name="coding_question_id", nullable=true)
    private String codingQuestionId;

    private String description;

    @Column(name="execution_time_limit")
    private BigDecimal executionTimeLimit;

    private String input1;
    private String input2;

    @Column(name="memory_limit")
    private Long memoryLimit;

    private String output;

    @Enumerated(EnumType.STRING)
    @Column(name="testcase_type")
    private TestcaseType testcaseType;

    private Integer weightage;

    public enum TestcaseType {
        PUBLIC,PRIVATE
    }

    public String getTestcaseId() {
        return testcaseId;
    }
    public void setTestcaseId(String testcaseId) {
        this.testcaseId = testcaseId;
    }
    public String getCodingQuestionId() {
        return codingQuestionId;
    }
    public void setCodingQuestionId(String codingQuestionId) {
        this.codingQuestionId = codingQuestionId;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public BigDecimal getExecutionTimeLimit() {
        return executionTimeLimit;
    }
    public void setExecutionTimeLimit(BigDecimal executionTimeLimit) {
        this.executionTimeLimit = executionTimeLimit;
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
    public Long getMemoryLimit() {
        return memoryLimit;
    }
    public void setMemoryLimit(Long memoryLimit) {
        this.memoryLimit = memoryLimit;
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
    public Integer getWeightage() {
        return weightage;
    }
    public void setWeightage(Integer weightage) {
        this.weightage = weightage;
    }
}