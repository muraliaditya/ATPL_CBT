package com.aaslin.cbt.super_admin.service;

import com.aaslin.cbt.common.model.CodingQuestions;
import com.aaslin.cbt.common.model.Testcases;
import com.aaslin.cbt.super_admin.dto.*;
import com.aaslin.cbt.super_admin.exceptions.CodingQuestionNotFoundException;
import com.aaslin.cbt.super_admin.repository.CodingQuestionsRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CodingQuestionGenerationServiceImpl implements CodingQuestionGenerationService {

    private final CodingQuestionsRepository codingQuestionsRepository;
    private final ObjectMapper objectMapper;

    private List<String> parseStringListJson(String json) {
        try {
            if (json == null || json.isEmpty()) return List.of();
            return objectMapper.readValue(json, new TypeReference<List<String>>() {});
        } catch (Exception e) {
            return List.of();
        }
    }

    private Object parseJsonToObject(String json) {
        try {
            if (json == null || json.isEmpty()) return null;
            return objectMapper.readValue(json, Object.class);
        } catch (Exception e) {
            return json;
        }
    }

    private GeneratedCodingQuestionResponse mapToResponse(CodingQuestions cq) {
        GeneratedCodingQuestionResponse dto = new GeneratedCodingQuestionResponse();
        dto.setCodingQuestionId(cq.getCodingQuestionId());
        dto.setQuestionName(cq.getQuestion());
        dto.setQuestionDescription(cq.getDescription());
        dto.setJavaBoilerCode(cq.getJavaBoilerCode());
        dto.setPythonBoilerCode(cq.getPythonBoilerCode());
        dto.setInputParams(parseStringListJson(cq.getInputParams()));
        dto.setInputType(parseStringListJson(cq.getInputType()));
        dto.setOutputType(cq.getOutputFormat());

        List<TestcaseResponse> tlist = new ArrayList<>();
        if (cq.getTestcases() != null) {
            for (Testcases tc : cq.getTestcases()) {
                TestcaseResponse tr = new TestcaseResponse();
                tr.setTestcaseId(tc.getTestcaseId());
                tr.setInputValues(parseJsonToObject(tc.getInputValues()));
                tr.setExpectedOutput(parseJsonToObject(tc.getExpectedOutput()));
                tr.setTestcaseType(tc.getTestcaseType() != null ? tc.getTestcaseType().name() : null);
                tr.setWeightage(tc.getWeightage());
                tr.setDescription(tc.getDescription());
                tlist.add(tr);
            }
        }
        dto.setTestcases(tlist);
        return dto;
    }

    @Override
    public List<GeneratedCodingQuestionResponse> generateQuestions(GenerateCodingQuestionsRequest request) {
        List<GeneratedCodingQuestionResponse> out = new ArrayList<>();

        if (request.getPreferences() != null && !request.getPreferences().isEmpty()) {
            for (GenerateCodingQuestionsRequest.Preference p : request.getPreferences()) {
                String diff = Optional.ofNullable(p.getPreference()).orElse("EASY").toUpperCase();
                CodingQuestions cq = codingQuestionsRepository.findRandomByDifficulty(diff);
                if (cq == null) {
                    throw new CodingQuestionNotFoundException("No question found for difficulty: " + diff);
                }
                out.add(mapToResponse(cq));
            }
            return out;
        }

        int cnt = Optional.ofNullable(request.getCount()).orElse(1);
        for (int i = 0; i < cnt; i++) {
            CodingQuestions cq = codingQuestionsRepository.findRandomByDifficulty("EASY");
            if (cq == null) break;
            out.add(mapToResponse(cq));
        }
        return out;
    }

    @Override
    public GeneratedCodingQuestionResponse regenerateQuestion(String preference, String questionId) {
        String diff = Optional.ofNullable(preference).orElse("EASY").toUpperCase();
        CodingQuestions cq = codingQuestionsRepository.findRandomByDifficultyExcluding(diff, questionId);
        if (cq == null) {
            throw new CodingQuestionNotFoundException("No alternative available for difficulty: " + diff);
        }
        return mapToResponse(cq);
    }
}
