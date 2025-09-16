package com.aaslin.cbt.participant.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContestEligibilityResponse {
	public ContestEligibilityResponse(String contestId, String categoryName) {
		super();
		this.contestId = contestId;
		this.categoryName = categoryName;
	}
	private String contestId;
	private String categoryName;

}
