package com.aaslin.cbt.participant.dto;

import java.util.List;

import com.aaslin.cbt.common.model.Sections;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MCQSection {

	public MCQSection(Sections key, List<McqQuestionDto> mcqQuestions) {
		this.section=key.getSection();
		this.mcqQuestions=mcqQuestions;
	}
	private String section;
	private List<McqQuestionDto> mcqQuestions;
}
