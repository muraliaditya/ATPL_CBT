package com.aaslin.cbt.developer.service.Implementation;

import org.springframework.stereotype.Service;

import com.aaslin.cbt.common.model.CodingQuestions;
import com.aaslin.cbt.common.model.DeveloperCodingSubmissions.DeveloperCodingSubmissionStatus;
import com.aaslin.cbt.developer.Dto.TopDevelopersDto;
import com.aaslin.cbt.developer.Dto.TopDevelopersResponseDto;
import com.aaslin.cbt.developer.repository.CodingQuestionRepository;
import com.aaslin.cbt.developer.repository.DeveloperCodingSubmissionRepository;
import com.aaslin.cbt.developer.service.TopDevelopersService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TopDevelopersServiceImpl implements TopDevelopersService {

    private final DeveloperCodingSubmissionRepository developerCodingSubmissionsRepository;
    private final CodingQuestionRepository codingQuestionsRepository;

    @Override
    public TopDevelopersResponseDto getTopDevelopers() {
        List<TopDevelopersDto> topDevelopers = developerCodingSubmissionsRepository.findTopDevelopers(DeveloperCodingSubmissionStatus.SOLVED)
                .stream()
                .limit(10) 
                .toList();

        Long totalQuestions = codingQuestionsRepository.countByApprovalStatus(CodingQuestions.ApprovalStatus.APPROVED);

        return new TopDevelopersResponseDto(topDevelopers, totalQuestions);
    }
}