package com.aaslin.cbt.participant.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.aaslin.cbt.common.model.Contest;
import com.aaslin.cbt.participant.dto.CodingQuestionDto;
import com.aaslin.cbt.participant.dto.CodingResponse;
import com.aaslin.cbt.participant.dto.ContestDetailsResponse;
import com.aaslin.cbt.participant.dto.ContestEligibilityResponse;
import com.aaslin.cbt.participant.dto.ContestStartResponse;
import com.aaslin.cbt.participant.dto.MCQResponse;
import com.aaslin.cbt.participant.dto.MCQSection;
import com.aaslin.cbt.participant.dto.SectionResponse;
import com.aaslin.cbt.participant.dto.SubSectionResponse;
import com.aaslin.cbt.participant.repository.CodingQuestionsRepository;
import com.aaslin.cbt.participant.repository.ContestRepository;
import com.aaslin.cbt.participant.repository.MCQRepository;

@Service("ParticipantContestsService")
public class ContestService {

    private final ContestRepository contestRepo;
    private final MCQRepository mcqRepository;
    private final CodingQuestionsRepository codingRepository;

    public ContestService(ContestRepository contestRepo, 
                          MCQRepository mcqRepository, 
                          CodingQuestionsRepository codingRepository) {
        super();
        this.contestRepo = contestRepo;
        this.mcqRepository = mcqRepository;
        this.codingRepository = codingRepository;
    }

    public ContestEligibilityResponse checkEligibility(String contestId) {
        return contestRepo.findById(contestId)
                .map(contest -> new ContestEligibilityResponse(
                        contest.getContestId(),
                        contest.getCategory().getCategoryName()))
                .orElseThrow(() -> new IllegalArgumentException("Contest not found with id " + contestId));
    }

    public ContestDetailsResponse getContestInfo(String contestId) {
        Contest contest = contestRepo.findById(contestId)
                .orElseThrow(() -> new RuntimeException("Contest not found"));

        List<SectionResponse> sections = new ArrayList<>();
        sections.add(new SectionResponse(
                "Coding",
                contest.getTotalCodingQuestions(),
                contest.getTotalCodingQuestions() * 50,
                null
        ));

        List<SubSectionResponse> subSections = new ArrayList<>();
        subSections.add(new SubSectionResponse("Quantitative", contest.getTotalQuantsMcqs(), contest.getTotalQuantsMcqs() * 2));
        subSections.add(new SubSectionResponse("Reasoning", contest.getTotalReasoningMcqs(), contest.getTotalReasoningMcqs() * 2));
        subSections.add(new SubSectionResponse("Technical", contest.getTotalTechnicalMcqs(), contest.getTotalTechnicalMcqs() * 2));
        subSections.add(new SubSectionResponse("Verbal", contest.getTotalVerbalMcqs(), contest.getTotalVerbalMcqs() * 2));

        int totalMcqCount = contest.getTotalQuantsMcqs() + contest.getTotalReasoningMcqs()
                + contest.getTotalTechnicalMcqs() + contest.getTotalVerbalMcqs();
        int totalMcqMarks = subSections.stream().mapToInt(SubSectionResponse::getMarks).sum();
        sections.add(new SectionResponse("MCQ", totalMcqCount, totalMcqMarks, subSections));

        return new ContestDetailsResponse(contest.getContestName(), sections);
    }

    public ContestStartResponse startContest(String contestId) {
        Contest contest = contestRepo.findById(contestId)
                .orElseThrow(() -> new RuntimeException("Contest not found"));

        List<MCQSection> mcqSections = mcqRepository.findSectionsByContest(contestId);

        List<CodingQuestionDto> codingQuestions = codingRepository.findByContestId(contestId)
                .stream()
                .map(q -> new CodingQuestionDto(
                        q.getCodingQuestionId(),
                        q.getQuestion(),
                        q.getDescription(),
                        q.getJavaBoilerCode(),
                        q.getPythonBoilerCode(),
                        q.getInputParams(),
                        q.getInputType(),
                        q.getOutputFormat()
                ))
                .toList();

        return new ContestStartResponse(
                contest.getContestId(),
                contest.getContestName(),
                contest.getStatus().name(),
                contest.getStartTime(),
                contest.getEndTime(),
                contest.getDuration(),
                contest.getCategory(),
                new MCQResponse(mcqSections),
                new CodingResponse(codingQuestions)
        );
    }
}