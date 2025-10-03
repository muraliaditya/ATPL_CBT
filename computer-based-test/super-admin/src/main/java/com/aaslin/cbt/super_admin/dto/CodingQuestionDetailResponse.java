package com.aaslin.cbt.super_admin.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class CodingQuestionDetailResponse {
    private String codingQuestionId;
    private String question;
    private String description;
    private String difficulty;
    private String methodName;
    private String javaBoilerCode;
    private String pythonBoilerCode;
    private BigDecimal executionTimeLimit;
    private Long memoryLimit;
    private List<String> parameterNames;
    private List<String> inputTypes;
    private Boolean isActive;
    private Integer weightage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<TestcaseResponse> testcases;
}
