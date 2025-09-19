package com.aaslin.cbt.participant.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubmissionResponse {

	private String codeStatus;
	private String message;
	private String code;
	public Integer publicTestcasePassed;
	public Integer privateTestcasePassed;
	private List<TestcaseResultResponse> publicTestcaseResults;
	private List<TestcaseResultResponse> privateTestcaseResults;
}
