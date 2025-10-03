package com.aaslin.cbt.super_admin.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.aaslin.cbt.common.model.McqQuestion;
import com.aaslin.cbt.common.model.Section;
import com.aaslin.cbt.super_admin.dto.McqQuestionDTO;
import com.aaslin.cbt.super_admin.exceptions.CustomExceptions.McqNotFoundException;
import com.aaslin.cbt.super_admin.exceptions.CustomExceptions.SectionNotFoundException;
import com.aaslin.cbt.super_admin.idgenerator.McqIdGenerator;
import com.aaslin.cbt.super_admin.repository.McqQuestionRepository;
import com.aaslin.cbt.super_admin.repository.SectionRepository;
import com.aaslin.cbt.super_admin.util.AuditHelper;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class McqQuestionService {

    private final McqQuestionRepository mcqRepo;
    private final SectionRepository sectionRepo;
    private final AuditHelper mcqAuditHelper;
    private final ModelMapper modelMapper;

    private McqQuestionDTO mapToDTO(McqQuestion mcq) {
        McqQuestionDTO dto = modelMapper.map(mcq, McqQuestionDTO.class);
        if (mcq.getSection() != null) {
            dto.setSection(mcq.getSection().getSection());
        }
        return dto;
    }

    private McqQuestion toEntity(McqQuestionDTO dto) {
        McqQuestion entity = modelMapper.map(dto, McqQuestion.class);
        if (dto.getSection() != null) {
            Section section = getSectionOrThrow(dto.getSection());
            entity.setSection(section);
        }
        mcqAuditHelper.applyAuditForMcqQuestion(entity);
        return entity;
    }

    private Section getSectionOrThrow(String sectionName) {
        return sectionRepo.findBySection(sectionName)
                .orElseThrow(() -> new SectionNotFoundException("Invalid section name: " + sectionName));
    }

    private McqQuestion getMcqOrThrow(String mcqId) {
        return mcqRepo.findById(mcqId)
                .orElseThrow(() -> new McqNotFoundException("MCQ not found: " + mcqId));
    }

    public McqQuestion getMcqById(String mcqId) {
        return getMcqOrThrow(mcqId);
    }

    public List<McqQuestionDTO> getQuestionsBySection(String sectionName) {
        Section section = getSectionOrThrow(sectionName);
        return mcqRepo.findBySectionAndIsActiveTrue(section).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
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

        return mcqRepo.saveAll(entities).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public McqQuestionDTO updateMcq(String id, McqQuestionDTO dto) {
        McqQuestion existing = getMcqOrThrow(id);
        McqQuestion updated = toEntity(dto);
        existing.setQuestionText(updated.getQuestionText());
        existing.setOption1(updated.getOption1());
        existing.setOption2(updated.getOption2());
        existing.setOption3(updated.getOption3());
        existing.setOption4(updated.getOption4());
        existing.setAnswerKey(updated.getAnswerKey());
        existing.setWeightage(updated.getWeightage());
        existing.setSection(updated.getSection());

        mcqAuditHelper.applyAuditForMcqQuestion(existing);
        return mapToDTO(mcqRepo.save(existing));
    }
    public void safeDelete(String id) {
        McqQuestion mcq = getMcqOrThrow(id);
        mcq.setIsActive(false);
        mcqRepo.save(mcq);
    }

    public List<McqQuestionDTO> generateRandomQuestions(String sectionName, int count) {
        Section section = getSectionOrThrow(sectionName);
        List<McqQuestion> randomList = mcqRepo.findRandomBySection(section.getSectionId(), count);
        return randomList.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public McqQuestionDTO regenerateQuestion(String sectionName, String currentQuestionId) {
        Section section = getSectionOrThrow(sectionName);
        McqQuestion mcq = mcqRepo.findRandomOtherBySection(section.getSectionId(), currentQuestionId);
        return mcq != null ? mapToDTO(mcq) : null;
    }
}
