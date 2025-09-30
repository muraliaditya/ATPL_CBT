package com.aaslin.cbt.participant.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MCQResponse {

	private List<MCQSection> mcqSections;
}
