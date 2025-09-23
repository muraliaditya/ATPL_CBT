package com.aaslin.cbt.super_admin.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.aaslin.cbt.common.model.Contest;
import com.aaslin.cbt.common.model.Submission;
import com.aaslin.cbt.super_admin.dto.ContestResultDTO;
import com.aaslin.cbt.super_admin.dto.ContestResultsResponse;
import com.aaslin.cbt.super_admin.repository.ContestRepository;
import com.aaslin.cbt.super_admin.repository.SubmissionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContestResultService {

    private final SubmissionRepository submissionRepo;
    private final ContestRepository contestRepo;

    public ContestResultsResponse getContestResults(String contestId) {
        
        Contest contest = contestRepo.findById(contestId)
                .orElseThrow(() -> new RuntimeException("Contest not found"));

       
        String eligibility = contest.getCategory().getCategoryName(); 
        
        List<Submission> submissions = submissionRepo.findByContestContestId(contestId);

        List<ContestResultDTO> results = submissions.stream().map(sub -> {
            ContestResultDTO dto = new ContestResultDTO();
            dto.setSubmissionId(sub.getSubmissionId());
            dto.setParticipantId(sub.getParticipant().getParticipantId());
            dto.setUsername(sub.getParticipant().getName());
            dto.setEmail(sub.getParticipant().getEmail());
            dto.setCodingMarks(sub.getTotalCodingScore());
            dto.setMcqMarks(sub.getTotalMcqScore());
            dto.setTotalMarks(sub.getTotalScore());

            if ("Student".equalsIgnoreCase(eligibility)) {
                dto.setCollege(sub.getParticipant().getCollege().getCollegeName());
                dto.setCollegeRegdNo(sub.getParticipant().getCollegeRegdNo());
                if (sub.getParticipant().getPercentage() != null) {
                    dto.setPercentage(sub.getParticipant().getPercentage().doubleValue());
                }
            } else if ("Experienced".equalsIgnoreCase(eligibility)) {
                dto.setCompany(sub.getParticipant().getCompany().getCurrentCompanyName());
                dto.setDesignation(sub.getParticipant().getDesignation().getDesignationName());
                dto.setOverallExperience(sub.getParticipant().getOverallExperience());
            }
            return dto;
        }).collect(Collectors.toList());
        return ContestResultsResponse.builder()
                .eligibility(eligibility)
                .results(results)
                .build();
    }
}

