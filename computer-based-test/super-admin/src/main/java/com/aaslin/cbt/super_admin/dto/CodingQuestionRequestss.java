package com.aaslin.cbt.super_admin.dto;

import java.util.List;

import lombok.Data;

@Data
public class CodingQuestionRequestss {
    private String question;
    private String description;
    private String difficulty;
    private String title;
    private List<String> parameterNames; 
    private List<String> inputTypes;     
    private String javaBoilerCode;
    private String pythonBoilerCode;
    private String methodName;
    private List<TestcaseRequestsss> testcases;
    private Boolean isActive;
    private String codingQuestionId; 
    private Integer weightage;
}