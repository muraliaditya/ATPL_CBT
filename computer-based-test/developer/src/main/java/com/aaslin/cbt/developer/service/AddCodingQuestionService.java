package com.aaslin.cbt.developer.service;

import com.aaslin.cbt.developer.Dto.AddCodingQuestionRequestDto;
import com.aaslin.cbt.developer.Dto.AddCodingQuestionResponse;

public interface AddCodingQuestionService {
	AddCodingQuestionResponse addCodingQuestion(AddCodingQuestionRequestDto request, String createdByUserId);
}
