package com.aaslin.cbt.developer.service.Implementation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aaslin.cbt.common.model.CodingQuestions;
import com.aaslin.cbt.developer.Dto.CodingQuestionDto;
import com.aaslin.cbt.developer.Dto.CodingQuestionResponse;
import com.aaslin.cbt.developer.repository.CodingQuestionRepository;
import com.aaslin.cbt.developer.service.CodingQuestionService;

import java.util.List;

@Service
public class CodingQuestionServiceImpl implements CodingQuestionService {

    @Autowired
    private CodingQuestionRepository codingQuestionRepo;

    public CodingQuestionResponse searchQuestions(String question, int page) {
    	
    	int pageIndex = (page > 0) ? page - 1 : 0;
        Pageable pageable = PageRequest.of(pageIndex, 10); // 10 results per page
        Page<CodingQuestions> questions;

        if (question != null && !question.isEmpty()) {
            // Search,Pagination
            questions = codingQuestionRepo.findByQuestionContainingIgnoreCaseAndApprovalStatus(
                    question, CodingQuestions.ApprovalStatus.APPROVED, pageable);
        } else {
            //Default Load, Pagination
            questions = codingQuestionRepo.findByApprovalStatus(CodingQuestions.ApprovalStatus.APPROVED, pageable);
        }

        List<CodingQuestionDto> dtoList = questions.stream()
                .map(q -> new CodingQuestionDto(
                        q.getCodingQuestionId(),
                        q.getQuestion(),
                        q.getDifficulty()))
                .toList();

        return new CodingQuestionResponse(page, dtoList,
        		questions.getTotalElements(),  
                questions.getTotalPages() );
    }
}
