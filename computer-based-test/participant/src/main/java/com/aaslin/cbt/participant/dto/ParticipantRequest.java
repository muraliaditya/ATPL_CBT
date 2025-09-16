package com.aaslin.cbt.participant.dto;

import java.math.BigDecimal;

import com.aaslin.cbt.common.model.Category;
import com.aaslin.cbt.common.model.College;
import com.aaslin.cbt.common.model.Company;

import lombok.Data;

@Data
public class ParticipantRequest {
	private String participantId;
	private String name;
	private String email;
	private Category category;
	private String collegeRegdNo;
	private College college;
	private String highestDegree;
	private BigDecimal percentage;
	private Company company;
	private String overallExperience;
	private String designationName;
	private String contestId;
	 
}



