package com.aaslin.cbt.super_admin.service;

import com.aaslin.cbt.super_admin.dto.AssignCodingQuestionsRequest;
import com.aaslin.cbt.super_admin.dto.ApiResponse;
import com.aaslin.cbt.super_admin.dto.ContestCodingResponse;
import com.aaslin.cbt.super_admin.dto.GenerateCodingQuestionsRequest;

import java.util.List;

public interface ContestCodingService {
    ApiResponse assignQuestions(AssignCodingQuestionsRequest request);
    ApiResponse removeQuestion(String contestId, String codingQuestionId);
    List<ContestCodingResponse> getQuestionsByContest(String contestId);
    ApiResponse autoAssignQuestions(String contestId, GenerateCodingQuestionsRequest request);

}

