package com.aaslin.cbt.developer.Dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class FetchCodingQuestionDto {
	
	private String codingQuestionId;
    private String questionName;
    private String questionDescription;
    private String javaBoilerCode;
    private String pythonBoilerCode;
    private Object inputParams;
    private Object inputType;
    private String outputType;
    private List<FetchTestcaseDto> testcases;
}
