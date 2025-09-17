package com.aaslin.cbt.participant.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class TestcaseResultResponse {

		
		private String testcaseId;
		private String input; 
		private String expectedOutput; 
		private String actualOutput;
		private String status;
		 private Integer weightage;

}
