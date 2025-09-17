package com.aaslin.cbt.participant.dto;

import lombok.Data;

@Data
public class SubSectionResponse {
	
	public SubSectionResponse(String type, int questionCount, int marks) {
		super();
		this.type = type;
		this.questionCount = questionCount;
		this.marks = marks;
	}
	private String type;
	private int questionCount;
	private int marks;
	

}
