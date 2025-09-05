package com.aaslin.code_runner.dto;

import lombok.Data;

@Data
public  class TestCase{
	private String input;
	private String expectedOutput;
	private int marks;
}