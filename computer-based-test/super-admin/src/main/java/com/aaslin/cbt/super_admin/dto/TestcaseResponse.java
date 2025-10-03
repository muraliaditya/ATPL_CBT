package com.aaslin.cbt.super_admin.dto;

import lombok.Data;

@Data
public class TestcaseResponse {
    private String testcaseId;
    private Object inputValues;       // <-- needed for setInputValues()
    private Object expectedOutput;    // <-- needed for setExpectedOutput()
    private String testcaseType;
    private Integer weightage;
    private String description;       // <-- needed for setDescription()
}
