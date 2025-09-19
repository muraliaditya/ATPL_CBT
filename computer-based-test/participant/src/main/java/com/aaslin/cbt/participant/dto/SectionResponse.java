package com.aaslin.cbt.participant.dto;

import java.util.List;

import lombok.Data;

@Data
public class SectionResponse {
	
	public SectionResponse(String sectionType, int questionCount, int totalMarks,
			List<SubSectionResponse> subSections) {
		super();
		this.sectionType = sectionType;
		this.questionCount = questionCount;
		this.totalMarks = totalMarks;
		this.subSections = subSections;
	}
	private String sectionType;
	private int questionCount;
	private int totalMarks;
	private List<SubSectionResponse> subSections;

}
