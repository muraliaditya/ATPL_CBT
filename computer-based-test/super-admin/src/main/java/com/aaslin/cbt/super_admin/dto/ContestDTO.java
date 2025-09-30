package com.aaslin.cbt.super_admin.dto;

import java.time.LocalDateTime;
import java.util.List;
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
	private String contestId;
	private String contestName;
	private Integer totalCodingQuestions;
	private Integer totalQuantsMcqs;
	private Integer totalReasoningMcqs;
	private Integer totalVerbalMcqs;
	private Integer totalTechnicalMcqs;
	private Integer duration;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private ContestStatus status;
	private String categoryName;
	private List<McqSectionDTO> mcqSections;
	private List<CodingQuestionRequest> codingQuestions;

}

