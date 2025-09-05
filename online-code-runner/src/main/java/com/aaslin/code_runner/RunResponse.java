package com.aaslin.code_runner;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RunResponse {

	private  String output;
	private  String error;
	private  int exitCode;
	private  String status;
	
}
