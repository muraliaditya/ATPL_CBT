package com.aaslin.cbt.participant.dto;

import lombok.Data;

@Data
public class SubmissionRequest {
	private String contestId;
	private String questionId;
	private String languageType;
	private String code;
	private String participantId;
	private Boolean isFinalAttempt;

}
