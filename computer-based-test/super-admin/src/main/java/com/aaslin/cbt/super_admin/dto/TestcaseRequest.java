package com.aaslin.cbt.super_admin.dto;
import lombok.Data;
@Data
public class TestcaseRequest {
    private String testcaseId;
    private String inputs;  
    private String output;               
    private Integer weightage;
    private String testcaseType;
}
