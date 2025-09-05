package com.aaslin.code_runner.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class SubmissionResponse {

	private String codingSubmissionId;
	private String codingQuestionId;
	private String language;
	private int score;
	private String testCasesPassed;
	private String code;
	private boolean isFinal;
	private String status;
	private LocalDateTime submittedAt;
	private List<TestcaseResultDTO> testcaseResults;
	
	@Data
	public static class TestcaseResultDTO {
		private String testcaseId;
		private boolean passed;
		private int marksAwarded;

	}

}
