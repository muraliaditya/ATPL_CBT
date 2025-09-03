package com.aaslin.code_runner;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
public class RunResponse {

	private @Getter @Setter String stdout;
	private @Getter @Setter String stderr;
	private @Getter @Setter int exitCode;
	private @Getter @Setter String status;
	
}
