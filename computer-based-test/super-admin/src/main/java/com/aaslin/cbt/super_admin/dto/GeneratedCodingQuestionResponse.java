package com.aaslin.cbt.super_admin.dto;

import lombok.Data;
import java.util.List;

@Data
public class GeneratedCodingQuestionResponse {
    private String codingQuestionId;
    private String questionName;
    private String questionDescription;
    private String javaBoilerCode;
    private String pythonBoilerCode;
    private List<String> inputParams;
    private List<String> inputType;
    private String outputType;
    private List<TestcaseResponse> testcases;
}
