package com.aaslin.cbt.developer.Dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class TopDevelopersResponseDto {
	private List<TopDevelopersDto> topDevelopers;
	private Long totalQuestions;
}