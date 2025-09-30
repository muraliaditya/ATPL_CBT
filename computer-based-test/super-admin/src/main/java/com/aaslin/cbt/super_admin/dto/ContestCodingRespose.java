package com.aaslin.cbt.super_admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContestCodingRespose {

	private String contestId;
	private String codingQuestionId;
	private String question;
	private String difficulty;
}
