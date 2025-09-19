package com.aaslin.cbt.participant.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ParticipantRequest {
	private String participantId;
	private String name;
	private String email;
	private String categoryName;
	private String collegeRegdNo;
	private String collegeName;
	private String highestDegree;
	private BigDecimal percentage;
	private String currentCompanyName;
	private String overallExperience;
	private String designationName;
	private String contestId;
	 
}



