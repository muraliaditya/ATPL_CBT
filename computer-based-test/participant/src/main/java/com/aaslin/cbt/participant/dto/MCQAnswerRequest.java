package com.aaslin.cbt.participant.dto;

import lombok.Data;

@Data
public class MCQAnswerRequest {

	private String mcqId;
	private String selectedOption;
}
