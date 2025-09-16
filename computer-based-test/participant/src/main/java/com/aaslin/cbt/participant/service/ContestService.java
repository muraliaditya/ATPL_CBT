package com.aaslin.cbt.participant.service;

import org.springframework.stereotype.Service;

import com.aaslin.cbt.participant.dto.ContestEligibilityResponse;
import com.aaslin.cbt.participant.repository.ContestRepository;

@Service
public class ContestService {
	
	private final ContestRepository contestRepo;

	public ContestService(ContestRepository contestRepo) {
		super();
		this.contestRepo = contestRepo;
	}
	
	public ContestEligibilityResponse checkEligibility(String contestId) {
					
			return contestRepo.findById(contestId).map(contest-> new ContestEligibilityResponse(contest.getContestId(),contest.getCategory().getCategoryName()))
					.orElseThrow(()-> new IllegalArgumentException("contest not found with id" +contestId));
	}

}
