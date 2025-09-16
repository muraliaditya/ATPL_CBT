package com.aaslin.cbt.developer.Dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class CodingQuestionResponse {
	
    private int pageNo;
    private List<CodingQuestionDto> codingQuestions;
    private long totalRecords;   
    private int totalPages; 

    public CodingQuestionResponse(int pageNo, List<CodingQuestionDto> codingQuestions,long totalRecords, int totalPages) {
        this.pageNo = pageNo;
        this.codingQuestions = codingQuestions;
        this.totalRecords = totalRecords;
        this.totalPages = totalPages;
    }

}
