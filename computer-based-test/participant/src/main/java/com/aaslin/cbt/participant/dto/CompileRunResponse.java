package com.aaslin.cbt.participant.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompileRunResponse {
	
	public CompileRunResponse(String status, String message) {
		
		this.codeStatus=status;
		this.message=message;
		this.publicTestcasePassed=0;
		this.publicTestcaseResults=List.of();
	}
	private String codeStatus;
	private String message;
	private Integer publicTestcasePassed;
	private List<TestcaseResultResponse> publicTestcaseResults;

}
