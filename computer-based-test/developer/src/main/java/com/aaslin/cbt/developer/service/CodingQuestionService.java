package com.aaslin.cbt.developer.service;

import com.aaslin.cbt.developer.Dto.CodingQuestionResponse;

public interface CodingQuestionService{
	CodingQuestionResponse searchQuestions(String question, int page);
}
