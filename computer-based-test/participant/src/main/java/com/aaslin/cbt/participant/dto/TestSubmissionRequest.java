package com.aaslin.cbt.participant.dto;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.Data;

@Data
public class TestSubmissionRequest {

	private String contestId;
	private String participantId;
	private LocalDateTime submittedAt;
	private Map<String,String> mcqAnswers;
	private Map<String,SubmissionRequest> codingSubmissions;
}
