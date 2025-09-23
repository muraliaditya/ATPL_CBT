package com.aaslin.cbt.super_admin.dto;

import java.util.Map;

import lombok.Data;
@Data
public class TestcaseRequest {
    private String testcaseId;
    private Map<String, Object> inputs;  
    private Object output;               
    private Integer weightage;
    private String testcaseType;
}
