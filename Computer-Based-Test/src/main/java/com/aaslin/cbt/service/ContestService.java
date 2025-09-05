package com.aaslin.cbt.service;

import com.aaslin.cbt.dto.ContestInfoResponseDto;

public interface ContestService {
    ContestInfoResponseDto getContestDetails(String contestId);
}

