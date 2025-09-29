package com.aaslin.cbt.developer.service;

import com.aaslin.cbt.developer.Dto.CompileRunResponseDto;
import com.aaslin.cbt.developer.Dto.SubmitResponseDto;

public interface CompilerService {
	CompileRunResponseDto compileRun(String userId, String questionId, String languageType, String code);
	SubmitResponseDto submit(String userId, String questionId, String languageType, String code);
}
