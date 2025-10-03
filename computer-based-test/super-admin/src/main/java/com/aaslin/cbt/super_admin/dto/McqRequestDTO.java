package com.aaslin.cbt.super_admin.dto;
import com.aaslin.cbt.common.model.McqQuestion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class McqRequestDTO {
	private String mcqId;
	private String questionText;
	private String createdBy;
	private McqQuestion.ApprovalStatus approvalstatus;

}
