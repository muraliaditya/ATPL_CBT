package com.aaslin.cbt.participant.dto;

import java.time.LocalDateTime;

import com.aaslin.cbt.common.model.Category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ContestStartResponse {

	private String contestId;
	private String contestName;
	private String status;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private int duration;
	private Category category;
	private MCQResponse mcqs;
	private CodingResponse coding;
}
