package com.aaslin.cbt.super_admin.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CodingQuestionResponse {
    private String codingQuestionId;
    private String question;
    private String description;
    private String difficulty;
    private String outputFormat;
    private String methodName;
    private String javaBoilerCode;
    private String pythonBoilerCode;
    private Double executionTimeLimit;
    private Long memoryLimit;
    private List<String> inputTypes;
    private List<String> parameterNames;
    private Boolean isActive;
    private Integer weightage;
    private List<TestcaseResponse> testcases; // already exists in your dto package
}
