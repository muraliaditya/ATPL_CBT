package com.aaslin.cbt.developer.service.Implementation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aaslin.cbt.common.model.McqQuestion;
import com.aaslin.cbt.common.model.Section;
import com.aaslin.cbt.developer.Dto.AddMcqQuestionRequestDto;
import com.aaslin.cbt.developer.Dto.AddMcqQuestionResponse;
import com.aaslin.cbt.developer.mapper.McqQuestionMapper;
import com.aaslin.cbt.developer.repository.McqQuestionsRepository;
import com.aaslin.cbt.developer.repository.Sectionrepository;
import com.aaslin.cbt.developer.service.AddMcqQuestionService;
import com.aaslin.cbt.developer.util.CustomIdGenerator;
import com.aaslin.cbt.super_admin.util.AuditHelper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddMcqQuestionServiceImpl implements AddMcqQuestionService {

    private final McqQuestionsRepository mcqQuestionsRepository;
    private final Sectionrepository sectionRepository;
    private final AuditHelper auditHelper;
    private final McqQuestionMapper mcqQuestionMapper;

    @Override
    @Transactional
    public AddMcqQuestionResponse addMcqQuestions(AddMcqQuestionRequestDto request) {
        if (request.getMcqQuestions() == null || request.getMcqQuestions().isEmpty()) {
            throw new IllegalArgumentException("At least one MCQ question is required");
        }
        
        String lastId = mcqQuestionsRepository.findTopByOrderByMcqQuestionIdDesc()
                .map(McqQuestion::getMcqQuestionId)
                .orElse(null);

        for (AddMcqQuestionRequestDto.McqQuestionDto dto : request.getMcqQuestions()) {
        	
        	String lastSectionId = sectionRepository.findTopByOrderBySectionIdDesc()
        	        .map(Section::getSectionId)
        	        .orElse(null);
        	
            Section section = sectionRepository.findBySectionIgnoreCase(dto.getSection())
            		.orElseGet(() -> {
                        Section newSection = new Section();
                        newSection.setSectionId(CustomIdGenerator.generateNextId("SEC", lastSectionId)); 
                        newSection.setSection(dto.getSection());
                        newSection.setIsActive(true);
                        return sectionRepository.save(newSection);
                    });

            lastId = CustomIdGenerator.generateNextId("MCQ", lastId);

            McqQuestion mcq = new McqQuestion();
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