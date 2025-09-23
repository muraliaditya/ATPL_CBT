package com.aaslin.cbt.super_admin.service;

import com.aaslin.cbt.super_admin.dto.GenerateCodingQuestionsRequest;
import com.aaslin.cbt.super_admin.dto.GeneratedCodingQuestionResponse;
import java.util.List;

public interface CodingQuestionGenerationService {
    List<GeneratedCodingQuestionResponse> generateQuestions(GenerateCodingQuestionsRequest request);
    GeneratedCodingQuestionResponse regenerateQuestion(String preference, String questionId);
}

