package com.aaslin.cbt.participant.dto;

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
	private String inputParams; 
	private String inputType;
	private String outputFormat;
	
}
