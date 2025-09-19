package com.aaslin.cbt.super_admin.service;

import org.springframework.stereotype.Service;

import com.aaslin.cbt.common.model.McqQuestions;
import com.aaslin.cbt.common.model.Sections;
import com.aaslin.cbt.super_admin.Repository.McqQuestionRepository;
import com.aaslin.cbt.super_admin.Repository.SectionRepository;
import com.aaslin.cbt.super_admin.dto.McqQuestionDTO;
import com.aaslin.cbt.super_admin.idgenerator.McqIdGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class McqQuestionService {

    private final McqQuestionRepository mcqRepo;
    private final SectionRepository sectionRepo;

    public McqQuestionService(McqQuestionRepository mcqRepo, SectionRepository sectionRepo) {
        this.mcqRepo = mcqRepo;
        this.sectionRepo = sectionRepo;
    }

    public McqQuestionDTO mapToDTO(McqQuestions mcq) {
        McqQuestionDTO dto = new McqQuestionDTO();
        dto.setMcqId(mcq.getMcqQuestionId());
        dto.setQuestionText(mcq.getQuestionText());
        dto.setOption1(mcq.getOption1());
        dto.setOption2(mcq.getOption2());
        dto.setOption3(mcq.getOption3());
        dto.setOption4(mcq.getOption4());
        dto.setCorrectAnswer(mcq.getAnswerKey());
        dto.setSectionName(mcq.getSection() != null ? mcq.getSection().getSection() : null); 
        dto.setWeightage(mcq.getWeightage());
        return dto;
    }
    private McqQuestions toEntity(McqQuestionDTO dto) {
        McqQuestions entity = new McqQuestions();
        entity.setMcqQuestionId(dto.getMcqId());
        entity.setQuestionText(dto.getQuestionText());
        entity.setOption1(dto.getOption1());
        entity.setOption2(dto.getOption2());
        entity.setOption3(dto.getOption3());
        entity.setOption4(dto.getOption4());
        entity.setAnswerKey(dto.getCorrectAnswer());
        entity.setWeightage(dto.getWeightage());
        if (dto.getSectionName() != null) {
            Sections section = sectionRepo.findBySection(dto.getSectionName())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid section: " + dto.getSectionName()));
            entity.setSection(section);
        } else {
            entity.setSection(null);
        }

        return entity;
    }
    
    public List<McqQuestionDTO> getQuestionsBySection(String sectionName) {
        Sections section = sectionRepo.findBySection(sectionName)
                .orElseThrow(() -> new IllegalArgumentException("Invalid section name: " + sectionName));

        return mcqRepo.findBySectionAndDeletedFalse(section).stream()
        		.map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    public List<McqQuestionDTO> addQuestions(List<McqQuestionDTO> dtos) {
        int lastId = mcqRepo.findMaxIdNumber();
        List<McqQuestions> entities = new ArrayList<>();

        for (McqQuestionDTO dto : dtos) {
            McqQuestions entity = toEntity(dto);
            lastId++;
            entity.setMcqQuestionId(McqIdGenerator.generateMcqId(lastId));
            entities.add(entity);
        }

        List<McqQuestions> saved = mcqRepo.saveAll(entities);
        return saved.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public McqQuestionDTO updateMcq(String id, McqQuestionDTO dto) {
        McqQuestions existing = mcqRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("MCQ not found: " + id));

        existing.setQuestionText(dto.getQuestionText());
        existing.setOption1(dto.getOption1());
        existing.setOption2(dto.getOption2());
        existing.setOption3(dto.getOption3());
        existing.setOption4(dto.getOption4());
        existing.setAnswerKey(dto.getCorrectAnswer());
        existing.setWeightage(dto.getWeightage());

        
        if (dto.getSectionName() != null) {
            Sections section = sectionRepo.findBySection(dto.getSectionName())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid section: " + dto.getSectionName()));
            existing.setSection(section);
        }

        McqQuestions saved = mcqRepo.save(existing);
        return mapToDTO(saved);
    }

 
    public void safeDelete(String id) {
        McqQuestions mcq = mcqRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("MCQ not found: " + id));
        mcq.setDeleted(true);
        mcqRepo.save(mcq);
    }
    public McqQuestions getMcqById(String mcqId) {  
        return mcqRepo.findById(mcqId)
                .orElseThrow(() -> new RuntimeException("MCQ not found: " + mcqId));
    }

    public List<McqQuestionDTO> generateRandomQuestions(String sectionName, int count) {
        Sections section = sectionRepo.findBySection(sectionName)
                .orElseThrow(() -> new IllegalArgumentException("Invalid section name: " + sectionName));

        List<McqQuestions> randomList = mcqRepo.findRandomBySection(section.getSectionId(), count);
        if(randomList.isEmpty()) return new ArrayList<>();  

        return randomList.stream()
                .map(this::mapToDTO)
                .toList();
    }

    public McqQuestionDTO regenerateQuestion(String sectionName, String currentQuestionId) {
        Sections section = sectionRepo.findBySection(sectionName)
                .orElseThrow(() -> new IllegalArgumentException("Invalid section name: " + sectionName));

        McqQuestions mcq = mcqRepo.findRandomOtherBySection(section.getSectionId(), currentQuestionId);
        if(mcq == null) return null;  
        return mapToDTO(mcq);
    }
   }





