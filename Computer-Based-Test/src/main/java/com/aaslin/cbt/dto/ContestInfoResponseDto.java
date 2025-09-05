package com.aaslin.cbt.dto;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContestInfoResponseDto {
	private String ContestName;
	private int totalCodingQuestions;
	private int totalMcqQuestions;
	private LocalTime duration;
}
