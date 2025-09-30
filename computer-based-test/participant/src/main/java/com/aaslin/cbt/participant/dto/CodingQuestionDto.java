package com.aaslin.cbt.participant.dto;

import java.util.List;

import com.aaslin.cbt.common.model.Testcases;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CodingQuestionDto {

	private String codingQuestionId;
	private String questionName;
	private String description;
	private String javaBoilerCode;
	private String pythonBoilerCode;
	private List<String> inputParams; 
	private List<String> inputType;
	private String outputFormat;
	private List<TestcasesDTO> testcases;
	
}