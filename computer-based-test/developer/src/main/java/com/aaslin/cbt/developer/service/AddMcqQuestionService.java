package com.aaslin.cbt.developer.service;

import com.aaslin.cbt.developer.Dto.AddMcqQuestionRequestDto;
import com.aaslin.cbt.developer.Dto.AddMcqQuestionResponse;

public interface AddMcqQuestionService {
	
	AddMcqQuestionResponse addMcqQuestions(AddMcqQuestionRequestDto request, String userId);
}
