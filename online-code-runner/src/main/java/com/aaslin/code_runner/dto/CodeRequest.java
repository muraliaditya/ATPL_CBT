package com.aaslin.code_runner.dto;

import java.util.List;

import lombok.Data;

@Data
public class CodeRequest {

	public  String language;
	public  String code;
	public  List<TestCase> testCases;
	@Data
	public static class TestCase{
		public String input;
		public  String expectedOutput;
		public boolean isPublic;
		
		
		
	}
}
