package com.aaslin.cbt.participant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestcasesDTO {

	private String testcaseId;
	private String inputValue;
	private String expectedOutput;
	private String testcaseType;
	private int weightage;
	private String description;
}
