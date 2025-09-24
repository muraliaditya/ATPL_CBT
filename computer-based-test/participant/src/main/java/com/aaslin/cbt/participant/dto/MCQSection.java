package com.aaslin.cbt.participant.dto;

import java.util.List;

import com.aaslin.cbt.common.model.McqQuestions;

import lombok.Data;

@Data
public class MCQSection {

	private String section;
	private List<McqQuestions> mcqQuestions;
}
