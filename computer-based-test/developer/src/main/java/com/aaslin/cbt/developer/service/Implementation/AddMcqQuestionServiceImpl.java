package com.aaslin.cbt.developer.service.Implementation;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aaslin.cbt.common.model.McqQuestion;
import com.aaslin.cbt.common.model.Section;
import com.aaslin.cbt.developer.Dto.AddMcqQuestionRequestDto;
import com.aaslin.cbt.developer.exception.InvalidMcqRequestException;
import com.aaslin.cbt.developer.exception.McqDataAccessException;
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
    
    @Autowired
    private McqQuestionMapper mcqQuestionMapper;

    @Override
    @Transactional
    public Map<String,Object> addMcqQuestions(AddMcqQuestionRequestDto request) {
        if (request.getMcqQuestions() == null || request.getMcqQuestions().isEmpty()) {
            throw new InvalidMcqRequestException("Inavlid request make sure atleast one MCQ question is required");
        }
        
        try {
        	String lastId = mcqQuestionsRepository.findTopByOrderByMcqQuestionIdDesc()
        			.map(McqQuestion::getMcqQuestionId)
                	.orElse(null);

        	for (AddMcqQuestionRequestDto.McqQuestionDto mcqQuestionDto : request.getMcqQuestions()) {
        	
        		String lastSectionId = sectionRepository.findTopByOrderBySectionIdDesc()
        				.map(Section::getSectionId)
        				.orElse(null);
        	
        		Section section = sectionRepository.findBySectionIgnoreCase(mcqQuestionDto.getSection())
        				.orElseGet(() -> {
        					Section newSection = new Section();
        					newSection.setSectionId(CustomIdGenerator.generateNextId("SEC", lastSectionId)); 
        					newSection.setSection(mcqQuestionDto.getSection());
        					newSection.setIsActive(true);
        					return sectionRepository.save(newSection);
        				});

        		lastId = CustomIdGenerator.generateNextId("MCQ", lastId);

        		McqQuestion mcqQuestion = mcqQuestionMapper.toEntity(mcqQuestionDto);
        		mcqQuestion.setMcqQuestionId(lastId);
        		mcqQuestion.setSection(section);
        		auditHelper.applyAuditForMcqQuestion(mcqQuestion);
        		mcqQuestionsRepository.save(mcqQuestion);
        	}
        } catch(DataAccessException dataAccessException) {
        	throw new McqDataAccessException("Falied to save MCQ questions to database",dataAccessException);
        	
        } 
        return Map.of(
        		"statusCode", 200,
                "message",request.getMcqQuestions().size() + " MCQ questions added successfully",
                "status","success"
        );
    }
}