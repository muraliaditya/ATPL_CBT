package com.aaslin.cbt.developer.service;

import com.aaslin.cbt.developer.Dto.FetchCodingQuestionDto;

public interface FetchCodingQuestionService {
	
	FetchCodingQuestionDto getQuestionById(String questionId);
}
