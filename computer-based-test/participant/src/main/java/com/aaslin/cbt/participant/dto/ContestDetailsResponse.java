package com.aaslin.cbt.participant.dto;

import java.util.List;

import lombok.Data;

@Data
public class ContestDetailsResponse {
	private String contestName;
	private List<SectionResponse> sections;
	public ContestDetailsResponse(String contestName, List<SectionResponse> sectionResponse) {
		super();
		this.contestName = contestName;
		this.sections = sectionResponse;
	}
	

}
