package com.aaslin.cbt.super_admin.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.aaslin.cbt.common.model.CodingQuestions;
import com.aaslin.cbt.common.model.Testcases;
import com.aaslin.cbt.common.util.CustomIdGenerator;   
import com.aaslin.cbt.super_admin.dto.*;
import com.aaslin.cbt.super_admin.repository.CodingQuestionsRepository;
import com.aaslin.cbt.super_admin.repository.TestcasesRepository; 
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CodingQuestionServiceImpl implements CodingQuestionService {

    private final CodingQuestionsRepository codingQuestionsRepository;
    private final TestcasesRepository testcasesRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private String toJson(Object value) {
        try {
            return objectMapper.writeValueAsString(value != null ? value : Collections.emptyList());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize object to JSON", e);
        }
    }

    @Override
    public PaginatedCodingQuestionResponse search(String question, int page, int size) {
        int pageIndex = (page > 0) ? page - 1 : 0;

        Page<CodingQuestions> questions = codingQuestionsRepository.searchByQuestion(
                question, PageRequest.of(pageIndex, size));

        List<CodingQuestionSearchResponse> results = questions.getContent().stream()
                .map(q -> new CodingQuestionSearchResponse(
                        q.getCodingQuestionId(),
                        q.getQuestion(),
                        q.getDifficulty() != null ? q.getDifficulty().name() : "EASY"
                ))
                .toList();

        return new PaginatedCodingQuestionResponse(
                page, size, questions.getTotalElements(), questions.getTotalPages(), results
        );
    }

    @Override
    public ApiResponse addQuestion(CodingQuestionRequest request) {
        CodingQuestions cq = new CodingQuestions();
        cq.setQuestion(request.getQuestion());
        cq.setDescription(request.getDescription());
        cq.setDifficulty(CodingQuestions.Difficulty.valueOf(
                request.getDifficulty() != null ? request.getDifficulty().toUpperCase() : "EASY"
        ));
        cq.setOutputFormat("JSON");
        cq.setExecutionTimeLimit(BigDecimal.valueOf(1.0));
        cq.setMemoryLimit(256L);
        cq.setCreatedAt(LocalDateTime.now());
        cq.setMethodName(request.getMethodName());
        cq.setJavaBoilerCode(request.getJavaBoilerCode());
        cq.setPythonBoilerCode(request.getPythonBoilerCode());

        cq.setInputParams(toJson(request.getParameterNames()));
        cq.setInputType(toJson(request.getInputTypes()));

        String lastCQId = codingQuestionsRepository
                .findTopByOrderByCodingQuestionIdDesc()
                .map(CodingQuestions::getCodingQuestionId)
                .orElse(null);
        cq.setCodingQuestionId(CustomIdGenerator.generateNextId("CQ", lastCQId));

        String lastTcIdFromDb = testcasesRepository
                .findTopByOrderByTestcaseIdDesc()
                .map(Testcases::getTestcaseId)
                .orElse(null);

        AtomicReference<String> lastTcIdRef = new AtomicReference<>(lastTcIdFromDb);

        List<Testcases> testcaseEntities = Optional.ofNullable(request.getTestcases())
            .orElse(Collections.emptyList())
            .stream()
            .map(tcReq -> {
                Testcases t = new Testcases();
                String newId = CustomIdGenerator.generateNextId("TC", lastTcIdRef.get());
                t.setTestcaseId(newId);
                lastTcIdRef.set(newId);

                t.setCodingQuestion(cq);
                t.setInputValues(toJson(tcReq.getInputs()));
                t.setExpectedOutput(toJson(tcReq.getOutput()));
                t.setWeightage(tcReq.getWeightage());
                t.setTestcaseType(Testcases.TestcaseType.valueOf(tcReq.getTestcaseType().toUpperCase()));
                t.setCreatedAt(LocalDateTime.now());
                return t;
            })
            .collect(Collectors.toList());


        cq.setTestcases(testcaseEntities);

        codingQuestionsRepository.save(cq);
        return new ApiResponse("Coding question added successfully", "success");
    }

    @Override
    public ApiResponse updateQuestion(String id, CodingQuestionRequest request) {
        CodingQuestions cq = codingQuestionsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coding question not found"));

        cq.setQuestion(request.getQuestion());
        cq.setDescription(request.getDescription());
        cq.setDifficulty(CodingQuestions.Difficulty.valueOf(
                request.getDifficulty() != null ? request.getDifficulty().toUpperCase() : "EASY"
        ));
        cq.setUpdatedAt(LocalDateTime.now());

        cq.setInputParams(toJson(request.getParameterNames()));
        cq.setInputType(toJson(request.getInputTypes()));

        List<Testcases> existingList = Optional.ofNullable(cq.getTestcases()).orElse(new ArrayList<>());
        Map<String, Testcases> existingTcMap = existingList.stream()
                .filter(tc -> tc.getTestcaseId() != null)
                .collect(Collectors.toMap(Testcases::getTestcaseId, tc -> tc));

        List<Testcases> updatedList = new ArrayList<>();

        for (TestcaseRequest tcReq : Optional.ofNullable(request.getTestcases()).orElse(Collections.emptyList())) {
            if (tcReq.getTestcaseId() != null && existingTcMap.containsKey(tcReq.getTestcaseId())) {
                Testcases tc = existingTcMap.get(tcReq.getTestcaseId());
                tc.setInputValues(toJson(tcReq.getInputs()));
                tc.setExpectedOutput(toJson(tcReq.getOutput()));
                tc.setWeightage(tcReq.getWeightage());
                tc.setTestcaseType(Testcases.TestcaseType.valueOf(tcReq.getTestcaseType().toUpperCase()));
                tc.setUpdatedAt(LocalDateTime.now());
                updatedList.add(tc);
                existingTcMap.remove(tcReq.getTestcaseId());
            } else {
                Testcases tc = new Testcases();
                String lastTCId = testcasesRepository
                        .findTopByOrderByTestcaseIdDesc()
                        .map(Testcases::getTestcaseId)
                        .orElse(null);
                tc.setTestcaseId(CustomIdGenerator.generateNextId("TC", lastTCId));
                tc.setCodingQuestion(cq);
                tc.setInputValues(toJson(tcReq.getInputs()));
                tc.setExpectedOutput(toJson(tcReq.getOutput()));
                tc.setWeightage(tcReq.getWeightage());
                tc.setTestcaseType(Testcases.TestcaseType.valueOf(tcReq.getTestcaseType().toUpperCase()));
                tc.setCreatedAt(LocalDateTime.now());
                updatedList.add(tc);
            }
        }

   
        for (Testcases removed : existingTcMap.values()) {
            existingList.remove(removed);
        }

        cq.getTestcases().clear();
        cq.getTestcases().addAll(updatedList);

        codingQuestionsRepository.save(cq);
        return new ApiResponse("Coding question updated successfully", "success");
    }
}
