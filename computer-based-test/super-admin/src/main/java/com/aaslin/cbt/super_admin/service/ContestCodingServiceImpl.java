package com.aaslin.cbt.super_admin.service;

import com.aaslin.cbt.common.model.*;
import com.aaslin.cbt.super_admin.dto.*;
import com.aaslin.cbt.super_admin.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ContestCodingServiceImpl implements ContestCodingService {

    private final ContestRepository contestRepository;
    private final CodingQuestionsRepository codingQuestionsRepository;
    private final MapContestCodingRepository mapContestCodingRepository;
    private final CodingQuestionGenerationService codingQuestionGenerationService;

    @Override
    public ApiResponse assignQuestions(AssignCodingQuestionsRequest request) {
        Contest contest = contestRepository.findById(request.getContestId())
                .orElseThrow(() -> new RuntimeException("Contest not found"));

        for (String qId : request.getCodingQuestionIds()) {
            CodingQuestions question = codingQuestionsRepository.findById(qId)
                    .orElseThrow(() -> new RuntimeException("Question not found"));

            MapContestCoding mapping = new MapContestCoding();
            mapping.setContest(contest);
            mapping.setCodingQuestion(question);
            mapping.setAssignedAt(LocalDateTime.now());
            mapping.setCreatedAt(LocalDateTime.now());
            mapContestCodingRepository.save(mapping);
        }
        return new ApiResponse("Questions assigned successfully", "success");
    }
    
    
    @Override
    public ApiResponse autoAssignQuestions(String contestId, GenerateCodingQuestionsRequest request) {
        Contest contest = contestRepository.findById(contestId)
                .orElseThrow(() -> new RuntimeException("Contest not found"));

        List<GeneratedCodingQuestionResponse> generated =
                codingQuestionGenerationService.generateQuestions(request);

        for (GeneratedCodingQuestionResponse q : generated) {
            CodingQuestions cq = codingQuestionsRepository.findById(q.getCodingQuestionId())
                    .orElseThrow(() -> new RuntimeException("Coding Question not found"));

            MapContestCoding mapping = new MapContestCoding();
            mapping.setContestCodingMapId(UUID.randomUUID().toString());
            mapping.setContest(contest);
            mapping.setCodingQuestion(cq);
            mapping.setAssignedAt(LocalDateTime.now());
            mapping.setCreatedAt(LocalDateTime.now());
            mapContestCodingRepository.save(mapping);
        }

        return new ApiResponse("Random coding questions assigned successfully", "success");
    }

    

    @Override
    public ApiResponse removeQuestion(String contestId, String codingQuestionId) {
        Contest contest = contestRepository.findById(contestId)
                .orElseThrow(() -> new RuntimeException("Contest not found"));
        CodingQuestions question = codingQuestionsRepository.findById(codingQuestionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        mapContestCodingRepository.deleteByContestAndCodingQuestion(contest, question);
        return new ApiResponse("Question removed from contest", "success");
    }

    @Override
    public List<ContestCodingResponse> getQuestionsByContest(String contestId) {
        Contest contest = contestRepository.findById(contestId)
                .orElseThrow(() -> new RuntimeException("Contest not found"));

        return mapContestCodingRepository.findByContest(contest).stream()
                .map(m -> new ContestCodingResponse(
                        m.getContest().getContestId(),
                        m.getCodingQuestion().getCodingQuestionId(),
                        m.getCodingQuestion().getQuestion(),
                        m.getCodingQuestion().getDifficulty().name()
                )).collect(Collectors.toList());
    }
}
