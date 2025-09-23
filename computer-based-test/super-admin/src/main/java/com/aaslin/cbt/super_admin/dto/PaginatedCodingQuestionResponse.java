package com.aaslin.cbt.super_admin.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedCodingQuestionResponse {
    private int pageNo;
    private int pageSize;
    private long totalRecords;
    private int totalPages;
    private List<CodingQuestionSearchResponse> codingQuestions;
}
