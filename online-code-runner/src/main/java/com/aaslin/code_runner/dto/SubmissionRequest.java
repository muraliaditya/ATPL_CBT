package com.aaslin.code_runner.dto;

import java.util.List;

import lombok.Data;

@Data
public class SubmissionRequest {

	private String codingSubmissionId;
	private String codingQuestionId;
	private String language;
	private String code;
	private boolean isFinal;
	private List<TestCase> testCases;
	
}
