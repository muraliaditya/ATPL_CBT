package com.aaslin.code_runner;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RunResponse {

	private  String stdout;
	private  String stderr;
	private  int exitCode;
	private  String status;
	
}
