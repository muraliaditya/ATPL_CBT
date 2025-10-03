package com.aaslin.cbt.developer.service.Implementation;

import org.springframework.stereotype.Service;

import com.aaslin.cbt.common.model.CodingQuestion;
import com.aaslin.cbt.common.model.DeveloperCodingSubmission.DeveloperCodingSubmissionStatus;
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
    public TopDevelopersResponseDto getTopDevelopers(Integer limit) {
        
        int size = (limit != null && limit > 0) ? limit : 10;

        List<TopDevelopersDto> topDevelopers = developerCodingSubmissionsRepository
                .findTopDevelopers(DeveloperCodingSubmissionStatus.SOLVED)
                .stream()
                .limit(size)
                .toList();

        Long totalQuestions = codingQuestionsRepository
                .countByApprovalStatus(CodingQuestion.ApprovalStatus.APPROVED);

        return new TopDevelopersResponseDto(topDevelopers, totalQuestions);
    }
}
