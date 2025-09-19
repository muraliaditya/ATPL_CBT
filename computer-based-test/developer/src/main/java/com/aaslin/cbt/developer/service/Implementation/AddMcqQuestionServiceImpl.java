package com.aaslin.cbt.developer.service.Implementation;

import com.aaslin.cbt.common.model.McqQuestions;
import com.aaslin.cbt.common.model.Sections;
import com.aaslin.cbt.common.model.User;
import com.aaslin.cbt.developer.Dto.AddMcqQuestionRequestDto;
import com.aaslin.cbt.developer.Dto.AddMcqQuestionResponse;
import com.aaslin.cbt.developer.repository.McqQuestionsRepository;
import com.aaslin.cbt.developer.repository.Sectionrepository;
import com.aaslin.cbt.developer.service.AddMcqQuestionService;
import com.aaslin.cbt.developer.util.CustomIdGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AddMcqQuestionServiceImpl implements AddMcqQuestionService {

    @Autowired
    private McqQuestionsRepository mcqQuestionsRepository;

    @Autowired
    private Sectionrepository sectionRepository;

    @Override
    @Transactional
    public AddMcqQuestionResponse addMcqQuestions(AddMcqQuestionRequestDto request, String createdByUserId) {
        if (request.getMcqQuestions() == null || request.getMcqQuestions().isEmpty()) {
            throw new IllegalArgumentException("At least one MCQ question is required");
        }

        String lastId = mcqQuestionsRepository.findTopByOrderByMcqQuestionIdDesc()
                .map(McqQuestions::getMcqQuestionId)
                .orElse(null);

        LocalDateTime now = LocalDateTime.now();

        User user = new User();
        user.setUserId(createdByUserId);

        for (AddMcqQuestionRequestDto.McqQuestionDto dto : request.getMcqQuestions()) {
     
            Sections section = sectionRepository.findBySectionIgnoreCase(dto.getSection())
                    .orElseThrow(() -> new RuntimeException("Section not found: " + dto.getSection()));

            lastId = CustomIdGenerator.generateNextId("MCQ", lastId);

            McqQuestions mcq = new McqQuestions();
            mcq.setMcqQuestionId(lastId);
            mcq.setQuestionText(dto.getQuestion());
            mcq.setOption1(dto.getOption1());
            mcq.setOption2(dto.getOption2());
            mcq.setOption3(dto.getOption3());
            mcq.setOption4(dto.getOption4());
            mcq.setAnswerKey(dto.getAnswerKey());
            mcq.setSection(section);
            mcq.setWeightage(dto.getWeightage() != null ? dto.getWeightage() : 1);
            mcq.setApprovalStatus(McqQuestions.ApprovalStatus.PENDING);
            mcq.setDeleted(true);

            mcq.setCreatedBy(user);
            mcq.setUpdatedBy(user);
            mcq.setCreatedAt(now);
            mcq.setUpdatedAt(now);

            mcqQuestionsRepository.save(mcq);
        }

        return new AddMcqQuestionResponse(
                request.getMcqQuestions().size() + " MCQ questions added successfully",
                "success"
        );
    }
}