package com.aaslin.cbt.super_admin.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.aaslin.cbt.common.model.Category;
import com.aaslin.cbt.common.model.Contest.ContestStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContestDTO {
	private String ContestId;
	private String ContestName;
	private Integer totalCodingQuestions;
	private Integer totalQuantsMcqs;
	private Integer totalReasoningMcqs;
	private Integer totalVerbalMcqs;
	private Integer totalTechnicalMcqs;
	private Integer duration;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private ContestStatus status;
	private Category category;
	private List<McqSectionDTO> mcqSections;

}
