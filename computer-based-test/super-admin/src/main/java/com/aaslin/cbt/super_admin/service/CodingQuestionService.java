package com.aaslin.cbt.super_admin.service;

import com.aaslin.cbt.super_admin.dto.ApiResponse;
import com.aaslin.cbt.super_admin.dto.CodingQuestionRequest;
import com.aaslin.cbt.super_admin.dto.PaginatedCodingQuestionResponse;

public interface CodingQuestionService {
    PaginatedCodingQuestionResponse search(String question, int page, int size);
    ApiResponse addQuestion(CodingQuestionRequest request);
    ApiResponse updateQuestion(String id, CodingQuestionRequest request);
}