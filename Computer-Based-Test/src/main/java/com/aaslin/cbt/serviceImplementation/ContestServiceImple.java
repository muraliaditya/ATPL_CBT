package com.aaslin.cbt.serviceImplementation;


import com.aaslin.cbt.dto.ContestInfoResponseDto;
import com.aaslin.cbt.entity.Contest;
import com.aaslin.cbt.repository.ContestRepository;
import com.aaslin.cbt.service.ContestService;
import org.springframework.stereotype.Service;

@Service
public class ContestServiceImple implements ContestService {

    private final ContestRepository contestRepository;

    public ContestServiceImple(ContestRepository contestRepository) {
        this.contestRepository = contestRepository;
    }

    @Override
    public ContestInfoResponseDto getContestDetails(String contestId) {
        Contest contest = contestRepository.findById(contestId)
                .orElseThrow(() -> new RuntimeException("Contest not found with id: " + contestId));

        return new ContestInfoResponseDto(
                contest.getContestName(),
                contest.getTotalCodingQuestions(),
                contest.getTotalMcqQuestions(),
                contest.getDuration()  // LocalTime → "HH:mm:ss"
        );
    }
}

