package com.aaslin.cbt.developer.service;

import java.util.Map;

import com.aaslin.cbt.developer.Dto.AddMcqQuestionRequestDto;

public interface AddMcqQuestionService {
	
	Map<String,Object> addMcqQuestions(AddMcqQuestionRequestDto request);
}
