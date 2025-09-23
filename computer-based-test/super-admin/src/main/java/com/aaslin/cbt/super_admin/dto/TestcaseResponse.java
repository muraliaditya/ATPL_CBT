package com.aaslin.cbt.super_admin.dto;

import lombok.Data;

@Data
public class TestcaseResponse {
    private String testcaseId;
    private Object inputValues;
    private Object expectedOutput;
    private String testcaseType;
    private String description;
    private Integer weightage;
}