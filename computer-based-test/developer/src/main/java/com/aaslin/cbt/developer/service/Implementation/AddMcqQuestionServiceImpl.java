package com.aaslin.cbt.developer.service.Implementation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aaslin.cbt.common.model.McqQuestions;
import com.aaslin.cbt.common.model.Sections;
import com.aaslin.cbt.developer.Dto.AddMcqQuestionRequestDto;
import com.aaslin.cbt.developer.Dto.AddMcqQuestionResponse;
import com.aaslin.cbt.developer.repository.McqQuestionsRepository;
import com.aaslin.cbt.developer.repository.Sectionrepository;
import com.aaslin.cbt.developer.service.AddMcqQuestionService;
import com.aaslin.cbt.developer.util.CustomIdGenerator;
import com.aaslin.cbt.super_admin.util.AuditHelper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddMcqQuestionServiceImpl implements AddMcqQuestionService {

    private McqQuestionsRepository mcqQuestionsRepository;
    private Sectionrepository sectionRepository;
    private AuditHelper auditHelper;

    @Override
    @Transactional
    public AddMcqQuestionResponse addMcqQuestions(AddMcqQuestionRequestDto request) {
        if (request.getMcqQuestions() == null || request.getMcqQuestions().isEmpty()) {
            throw new IllegalArgumentException("At least one MCQ question is required");
        }
        
        String lastId = mcqQuestionsRepository.findTopByOrderByMcqQuestionIdDesc()
                .map(McqQuestions::getMcqQuestionId)
                .orElse(null);

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
            mcq.setIsActive(true);
            auditHelper.applyAuditForMcqQuestion(mcq);
            mcqQuestionsRepository.save(mcq);
        }

        return new AddMcqQuestionResponse(
                request.getMcqQuestions().size() + " MCQ questions added successfully",
                "success"
        );
    }
}