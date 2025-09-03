package com.aaslin.code_runner.dto;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Data
public class CodeRequest {

	public @Getter @Setter String language;
	public @Getter @Setter String code;
	public @Getter @Setter List<TestCase> testCases;
	@Data
	public static class TestCase{
		public @Getter @Setter String input;
		public @Getter @Setter  String expectedOutput;
		public @Getter @Setter boolean isPublic;
		
		
		
	}
}
