package com.aaslin.cbt.super_admin.service;

import org.springframework.stereotype.Service;
import com.aaslin.cbt.common.model.McqQuestion;
import com.aaslin.cbt.common.model.Section;
import com.aaslin.cbt.super_admin.dto.McqQuestionDTO;
import com.aaslin.cbt.super_admin.idgenerator.McqIdGenerator;
import com.aaslin.cbt.super_admin.repository.McqQuestionRepository;
import com.aaslin.cbt.super_admin.repository.SectionRepository;
import com.aaslin.cbt.super_admin.util.AuditHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class McqQuestionService {
    private final McqQuestionRepository mcqRepo;
    private final SectionRepository sectionRepo;
    private final AuditHelper mcqAuditHelper;

    public McqQuestionService(McqQuestionRepository mcqRepo, SectionRepository sectionRepo, AuditHelper mcqAuditHelper) {
        this.mcqRepo = mcqRepo;
        this.sectionRepo = sectionRepo;
        this.mcqAuditHelper = mcqAuditHelper;
    }

    public McqQuestionDTO mapToDTO(McqQuestion mcq) {
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
    
    public List<McqQuestionDTO> getQuestionsBySection(String sectionName) {
        Section section = sectionRepo.findBySection(sectionName)
                .orElseThrow(() -> new IllegalArgumentException("Invalid section name: " + sectionName));

        return mcqRepo.findBySectionAndIsActiveFalse(section).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }


    private McqQuestion toEntity(McqQuestionDTO dto) {
        McqQuestion entity = new McqQuestion();
        entity.setMcqQuestionId(dto.getMcqId());
        entity.setQuestionText(dto.getQuestionText());
        entity.setOption1(dto.getOption1());
        entity.setOption2(dto.getOption2());
        entity.setOption3(dto.getOption3());
        entity.setOption4(dto.getOption4());
        entity.setAnswerKey(dto.getCorrectAnswer());
        entity.setWeightage(dto.getWeightage());

        if (dto.getSectionName() != null) {
            Section section = sectionRepo.findBySection(dto.getSectionName())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid section: " + dto.getSectionName()));
            entity.setSection(section);
        } else {
            entity.setSection(null);
        }

        mcqAuditHelper.applyAuditForMcqQuestion(entity);

        return entity;
    }

    public List<McqQuestionDTO> addQuestions(List<McqQuestionDTO> dtos) {
        int lastId = mcqRepo.findMaxIdNumber();
        List<McqQuestion> entities = new ArrayList<>();

        for (McqQuestionDTO dto : dtos) {
            McqQuestion entity = toEntity(dto);
            lastId++;
            entity.setMcqQuestionId(McqIdGenerator.generateMcqId(lastId));
            entities.add(entity);
        }

        List<McqQuestion> saved = mcqRepo.saveAll(entities);
        return saved.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public McqQuestionDTO updateMcq(String id, McqQuestionDTO dto) {
        McqQuestion existing = mcqRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("MCQ not found: " + id));

        existing.setQuestionText(dto.getQuestionText());
        existing.setOption1(dto.getOption1());
        existing.setOption2(dto.getOption2());
        existing.setOption3(dto.getOption3());
        existing.setOption4(dto.getOption4());
        existing.setAnswerKey(dto.getCorrectAnswer());
        existing.setWeightage(dto.getWeightage());

        if (dto.getSectionName() != null) {
            Section section = sectionRepo.findBySection(dto.getSectionName())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid section: " + dto.getSectionName()));
            existing.setSection(section);
        }

        mcqAuditHelper.applyAuditForMcqQuestion(existing);

        McqQuestion saved = mcqRepo.save(existing);
        return mapToDTO(saved);
    }

    public void safeDelete(String id) {
        McqQuestion mcq = mcqRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("MCQ not found: " + id));
        mcq.setIsActive(true);
        mcqRepo.save(mcq);
    }

    public McqQuestion getMcqById(String mcqId) {
        return mcqRepo.findById(mcqId)
                .orElseThrow(() -> new RuntimeException("MCQ not found: " + mcqId));
    }
    
    public List<McqQuestionDTO> generateRandomQuestions(String sectionName, int count) {
        Section section = sectionRepo.findBySection(sectionName)
                .orElseThrow(() -> new IllegalArgumentException("Invalid section name: " + sectionName));

        List<McqQuestion> randomList = mcqRepo.findRandomBySection(section.getSectionId(), count);
        if (randomList.isEmpty()) return new ArrayList<>();

        return randomList.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public McqQuestionDTO regenerateQuestion(String sectionName, String currentQuestionId) {
        Section section = sectionRepo.findBySection(sectionName)
                .orElseThrow(() -> new IllegalArgumentException("Invalid section name: " + sectionName));
        McqQuestion mcq = mcqRepo.findRandomOtherBySection(section.getSectionId(), currentQuestionId);
        if (mcq == null) return null;
        return mapToDTO(mcq);
    }
}