package com.aaslin.cbt.developer.Dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data  @AllArgsConstructor
public class RecentCodingQuestionResponse {
	
	private List<RecentCodingQuestionDto> recentQuestions;
	
}
