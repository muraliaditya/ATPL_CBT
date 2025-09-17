package com.aaslin.cbt.participant.dto;

import lombok.Data;

@Data
public class CompileRunRequest {
	private String questionId;
	private String languageType;
	private String code;

}
