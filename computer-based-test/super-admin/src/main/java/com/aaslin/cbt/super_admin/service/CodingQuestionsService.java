package com.aaslin.cbt.super_admin.service;

import com.aaslin.cbt.super_admin.dto.ApiResponse;
import com.aaslin.cbt.super_admin.dto.CodingQuestionRequestss;
import com.aaslin.cbt.super_admin.dto.CodingQuestionResponse;
import com.aaslin.cbt.super_admin.dto.PaginatedCodingQuestionResponse;

import java.util.List;


import com.aaslin.cbt.common.model.CodingQuestion;

import com.aaslin.cbt.common.model.CodingQuestion;
import com.aaslin.cbt.super_admin.dto.ApiResponse;
import com.aaslin.cbt.super_admin.dto.CodingQuestionRequestss;
import com.aaslin.cbt.super_admin.dto.PaginatedCodingQuestionResponse;


public interface CodingQuestionsService {
    PaginatedCodingQuestionResponse search(String question, int page, int size);
    ApiResponse addQuestion(CodingQuestionRequestss request);
    ApiResponse updateQuestion(String id, CodingQuestionRequestss request);

    ApiResponse deleteQuestion(String id);
    ApiResponse restoreQuestion(String id);
    CodingQuestion getById(String id);               
    List<String> deserializeList(String json);
    CodingQuestionResponse getQuestionById(String id); 

}
