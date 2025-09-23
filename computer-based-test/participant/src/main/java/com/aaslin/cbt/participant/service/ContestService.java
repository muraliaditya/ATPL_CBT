package com.aaslin.cbt.participant.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.aaslin.cbt.common.model.Contest;
import com.aaslin.cbt.participant.dto.ContestDetailsResponse;
import com.aaslin.cbt.participant.dto.ContestEligibilityResponse;
import com.aaslin.cbt.participant.dto.SectionResponse;
import com.aaslin.cbt.participant.dto.SubSectionResponse;
import com.aaslin.cbt.participant.repository.ContestRepository;

@Service("ParticipantContestsService")
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
	
	public ContestDetailsResponse getContestInfo(String contestId) {
		Contest contest=contestRepo.findById(contestId).orElseThrow(()-> new RuntimeException("contest not found"));
		
		List<SectionResponse> sections=new ArrayList<>();
		sections.add(new SectionResponse(
				"Coding",
				contest.getTotalCodingQuestions(),
				contest.getTotalCodingQuestions()*50,
				null
				));
		
		
		List<SubSectionResponse> subSections=new ArrayList<>();
		subSections.add(new SubSectionResponse("Quantitative",contest.getTotalQuantsMcqs(),contest.getTotalQuantsMcqs()*2));
		subSections.add(new SubSectionResponse("Reasoning",contest.getTotalReasoningMcqs(),contest.getTotalReasoningMcqs()*2));
		subSections.add(new SubSectionResponse("Technical",contest.getTotalTechnicalMcqs(),contest.getTotalTechnicalMcqs()*2));
		subSections.add(new SubSectionResponse("Verbal",contest.getTotalVerbalMcqs(),contest.getTotalVerbalMcqs()*2));
		
		
		int totalMcqCount=contest.getTotalQuantsMcqs()+contest.getTotalReasoningMcqs()+contest.getTotalTechnicalMcqs()+contest.getTotalVerbalMcqs();
		int totalMcqMarks=subSections.stream().mapToInt(SubSectionResponse :: getMarks).sum();
		sections.add(new SectionResponse("MCQ",totalMcqCount,totalMcqMarks,subSections));
		
		return new ContestDetailsResponse(contest.getContestName(),sections);
		
	}

}
