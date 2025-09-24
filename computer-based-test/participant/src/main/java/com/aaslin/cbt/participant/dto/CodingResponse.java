package com.aaslin.cbt.participant.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CodingResponse {

	private List<CodingQuestionDto> codingQuestions;
}
