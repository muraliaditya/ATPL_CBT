package com.aaslin.cbt.participant.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestSubmissionResponse {

	private String message;
	private String status;
	private String submissionId;
	private LocalDateTime submittedAt;
	private int totalMcqScore;
	private int totalCodingScore;
	private int score;
}
