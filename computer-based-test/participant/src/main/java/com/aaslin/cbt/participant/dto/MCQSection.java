package com.aaslin.cbt.participant.dto;

import java.util.List;

import com.aaslin.cbt.common.model.Section;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MCQSection {

	public MCQSection(Section key, List<McqQuestionDto> mcqQuestions) {
		this.section=key.getSection();
		this.mcqQuestions=mcqQuestions;
	}
	private String section;
	private List<McqQuestionDto> mcqQuestions;
}
