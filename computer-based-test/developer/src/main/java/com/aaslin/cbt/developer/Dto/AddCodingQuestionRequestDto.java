package com.aaslin.cbt.developer.Dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class AddCodingQuestionRequestDto {
	
	private String question;
    private String description;
    private String difficulty;
    private String inputParams;  // Frontend sends: "["nums","target"]"
    private String inputType;    // Frontend sends: "["array","int"]"
    private String outputFormat;
    private String javaBoilerCode;
    private String pythonBoilerCode;
    private String methodName;
    private BigDecimal executionTimeLimit;
    private Long memoryLimit;
    private Boolean isActive;

    private List<AddTestcaseRequestDto> testcases;
}
