package com.aaslin.cbt.developer.service.Implementation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aaslin.cbt.common.model.CodingQuestion;
import com.aaslin.cbt.developer.Dto.CodingQuestionDto;
import com.aaslin.cbt.developer.Dto.CodingQuestionResponse;
import com.aaslin.cbt.developer.repository.CodingQuestionRepository;
import com.aaslin.cbt.developer.service.CodingQuestionService;

import java.util.List;

@Service
public class CodingQuestionPageServiceImpl implements CodingQuestionService {

    @Autowired
    private CodingQuestionRepository codingQuestionRepo;

    @Override
    public CodingQuestionResponse searchQuestions(String question, String difficulty, int page, int size) {

    	int pageIndex = (page > 0) ? page - 1 : 0;
        int pageSize = (size > 0) ? size : 10;

        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<CodingQuestion> questions;

        CodingQuestion.Difficulty difficultyEnum = null;
        
        if (difficulty != null && !difficulty.isEmpty()) {
            try {
                difficultyEnum = CodingQuestion.Difficulty.valueOf(difficulty.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid difficulty level: " + difficulty);
            }
        }
        
        //question and difficulty
        if (difficultyEnum != null && question != null && !question.isEmpty()) {
            questions = codingQuestionRepo.findByQuestionContainingIgnoreCaseAndDifficultyAndApprovalStatus(
                    question, difficultyEnum, CodingQuestion.ApprovalStatus.APPROVED, pageable);
        
        } else if (difficultyEnum != null) {
            questions = codingQuestionRepo.findByDifficultyAndApprovalStatus(
                    difficultyEnum, CodingQuestion.ApprovalStatus.APPROVED, pageable);

        } else if (question != null && !question.isEmpty()) {
            questions = codingQuestionRepo.findByQuestionContainingIgnoreCaseAndApprovalStatus(
                    question, CodingQuestion.ApprovalStatus.APPROVED, pageable);

        } else {
            questions = codingQuestionRepo.findByApprovalStatus(
                    CodingQuestion.ApprovalStatus.APPROVED, pageable);
        }

        List<CodingQuestionDto> dtoList = questions.stream()
                .map(q -> new CodingQuestionDto(
                        q.getCodingQuestionId(),
                        q.getQuestion(),
                        q.getDifficulty()))
                .toList();

        return new CodingQuestionResponse(
                page,
                dtoList,
                questions.getTotalElements(),
                questions.getTotalPages()
        );
    }
}
