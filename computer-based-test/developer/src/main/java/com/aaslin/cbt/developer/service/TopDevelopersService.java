package com.aaslin.cbt.developer.service;

import com.aaslin.cbt.developer.Dto.TopDevelopersResponseDto;

public interface TopDevelopersService {
	TopDevelopersResponseDto getTopDevelopers(Integer limit);
}