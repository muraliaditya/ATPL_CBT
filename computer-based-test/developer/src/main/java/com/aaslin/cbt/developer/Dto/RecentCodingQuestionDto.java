package com.aaslin.cbt.developer.Dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class RecentCodingQuestionDto {
	private String CodingQuestionId;
	private String questionName;
	private String createdBy;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
