package com.aaslin.cbt.developer.service.Implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.aaslin.cbt.common.model.CodingQuestions;
import com.aaslin.cbt.developer.Dto.RecentCodingQuestionDto;
import com.aaslin.cbt.developer.Dto.RecentCodingQuestionResponse;
import com.aaslin.cbt.developer.repository.CodingQuestionRepository;
import com.aaslin.cbt.developer.service.RecentCodingQuestionService;

@Service
public class RecentCodingQuestionServiceImpl implements RecentCodingQuestionService {

    @Autowired
    private CodingQuestionRepository codingQuestionRepo;

    @Override
    public RecentCodingQuestionResponse getRecentQuestions(Integer limit) {
       
        int size = (limit != null && limit > 0) ? limit : 10;

        // Using PageRequest to simulate LIMIT size
        List<CodingQuestions> questions = codingQuestionRepo
                .findByApprovalStatusOrderByCreatedAtDesc(
                        CodingQuestions.ApprovalStatus.APPROVED,
                        PageRequest.of(0, size)
                );

        List<RecentCodingQuestionDto> recentQuestions = questions.stream()
                .map(q -> new RecentCodingQuestionDto(
                        q.getCodingQuestionId(),
                        q.getQuestion(),
                        q.getCreatedBy() != null ? q.getCreatedBy().getUserId() : null,
                        q.getCreatedAt(),
                        q.getUpdatedAt()))
                .toList();

        return new RecentCodingQuestionResponse(recentQuestions);
    }
}
