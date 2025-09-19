package com.aaslin.cbt.developer.Dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CodingQuestionResponse {
	
    private int pageNo;
    private List<CodingQuestionDto> codingQuestions;
    private long totalRecords;   
    private int totalPages; 

}
