package com.aaslin.cbt.developer.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class TopDevelopersDto {
	private String userId;
	private String userName;
	private Long solvedQuestionsCount;
}

