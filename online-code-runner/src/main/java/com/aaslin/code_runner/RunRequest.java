package com.aaslin.code_runner;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class RunRequest {

	private @Getter @Setter Language language;
	private  @Getter @Setter String code;
	private  @Getter @Setter String stdin;
	private  @Getter @Setter int timeLimitSec=3;

	
	
}
