package com.aaslin.code_runner.dto;

import lombok.Data;

@Data
public class CodeResponse {

	private String input;
	private String expectedOutput;
	private String actualOutput;
	private String error;
	private String status;
	private boolean passed;
}

