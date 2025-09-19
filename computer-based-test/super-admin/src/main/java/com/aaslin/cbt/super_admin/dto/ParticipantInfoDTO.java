package com.aaslin.cbt.super_admin.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParticipantInfoDTO {
	private String participantId;
	private String username;
	private String collegeRegdNo;
	private String collegeName;
	private Integer codingMarks;
	private Integer mcqMarks;
	private Integer totalMarks;
	private BigDecimal percentage;
	

}
