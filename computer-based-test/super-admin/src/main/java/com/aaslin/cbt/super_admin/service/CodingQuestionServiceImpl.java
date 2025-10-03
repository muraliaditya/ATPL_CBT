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
import com.aaslin.cbt.super_admin.exceptions.CodingQuestionNotFoundException;
import com.aaslin.cbt.super_admin.repository.CodingQuestionsRepository;
import com.aaslin.cbt.super_admin.repository.TestcasesRepository;
import com.aaslin.cbt.super_admin.util.AuditHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CodingQuestionServiceImpl implements com.aaslin.cbt.super_admin.service.CodingQuestionsService {

    private final CodingQuestionsRepository codingQuestionsRepository;
    private final TestcasesRepository testcasesRepository;
    private final AuditHelper auditHelper;
    private final ObjectMapper objectMapper;

    private String toJson(Object value) {
        return toJson(value, null);
    }

    private String toJson(Object value, String key) {
        try {
            if (value instanceof List<?>) {
                List<?> listValue = (List<?>) value;
                if (key != null && !key.isEmpty()) {
                    return objectMapper.writeValueAsString(Collections.singletonMap(key, listValue));
                } else {
                    return objectMapper.writeValueAsString(listValue);
                }
            } else {
                if (key != null && !key.isEmpty()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put(key, value);
                    return objectMapper.writeValueAsString(map);
                } else {
                    return objectMapper.writeValueAsString(value != null ? value : Collections.emptyList());
                }
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize object to JSON", e);
        }
    }

    public List<String> deserializeList(String json) {
        try {
            if (json == null || json.trim().isEmpty()) return new ArrayList<>();

            Map<String, Object> map = objectMapper.readValue(json, Map.class);
            if (!map.isEmpty()) {
                Object firstValue = map.values().iterator().next();
                if (firstValue instanceof List<?>) {
                    return ((List<?>) firstValue).stream()
                            .map(Object::toString)
                            .collect(Collectors.toList());
                }
            }

            return objectMapper.readValue(json, List.class);

        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize JSON to list", e);
        }
    }


    private TestcaseRequest convertToTestcaseRequest(TestcaseRequestsss tcReqsss) {
        TestcaseRequest tcReq = new TestcaseRequest();
        tcReq.setTestcaseId(tcReqsss.getTestcasesId());

        if (tcReqsss.getInputs() != null) {
            tcReq.setInputs(toJson(tcReqsss.getInputs()));
        }

        if (tcReqsss.getOutput() != null) {
            tcReq.setOutput(toJson(tcReqsss.getOutput()));
        }

        tcReq.setWeightage(tcReqsss.getWeightage());
        tcReq.setTestcaseType(tcReqsss.getTestcaseType());

        return tcReq;
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
                .collect(Collectors.toList());

        return new PaginatedCodingQuestionResponse(
                page, size, questions.getTotalElements(), questions.getTotalPages(), results
        );
    }

    @Override
    public ApiResponse addQuestion(CodingQuestionRequestss request) {
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
        cq.setInputParams(toJson(request.getParameterNames(), "params"));
        cq.setInputType(toJson(request.getInputTypes(), "types"));
        cq.setIsActive(request.getIsActive());
        cq.setWeightage(request.getWeightage());

        auditHelper.applyAuditForCodingQuestion(cq);

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

                Map<String, Object> inputMap = new LinkedHashMap<>();
                List<String> parameterNames = Optional.ofNullable(request.getParameterNames()).orElse(Collections.emptyList());
                List<Object> inputs = Optional.ofNullable(tcReq.getInputs()).orElse(Collections.emptyList());
                for (int i = 0; i < parameterNames.size() && i < inputs.size(); i++) {
                    inputMap.put(parameterNames.get(i), inputs.get(i));
                }
                t.setInputValues(toJson(inputMap));
                t.setExpectedOutput(toJson(tcReq.getOutput()));

                t.setWeightage(tcReq.getWeightage());
                t.setTestcaseType(Testcases.TestcaseType.valueOf(tcReq.getTestcaseType().toUpperCase()));
                t.setCreatedAt(LocalDateTime.now());

                auditHelper.applyAuditForTestcase(t);
                return t;
            })
            .collect(Collectors.toList());

        cq.setTestcases(testcaseEntities);
        codingQuestionsRepository.save(cq);

        return new ApiResponse("Coding question added successfully", "success");
    }

    @Override
    public ApiResponse updateQuestion(String id, CodingQuestionRequestss request) {
        CodingQuestions cq = codingQuestionsRepository.findById(id)
                .orElseThrow(() -> new CodingQuestionNotFoundException("Coding question not found with id: " + id));

        cq.setQuestion(request.getQuestion());
        cq.setDescription(request.getDescription());
        cq.setDifficulty(CodingQuestions.Difficulty.valueOf(
                request.getDifficulty() != null ? request.getDifficulty().toUpperCase() : "EASY"
        ));
        cq.setUpdatedAt(LocalDateTime.now());

        cq.setInputParams(toJson(request.getParameterNames(), "params"));
        cq.setInputType(toJson(request.getInputTypes(), "types"));

        auditHelper.applyAuditForCodingQuestion(cq);

        cq.getTestcases().clear();

        String lastTCId = testcasesRepository
                .findTopByOrderByTestcaseIdDesc()
                .map(Testcases::getTestcaseId)
                .orElse(null);
        AtomicReference<String> lastTcIdRef = new AtomicReference<>(lastTCId);

        List<Testcases> updatedTestcases = new ArrayList<>();
        for (TestcaseRequestsss tcReqsss : Optional.ofNullable(request.getTestcases()).orElse(Collections.emptyList())) {
            TestcaseRequest tcReq = convertToTestcaseRequest(tcReqsss);
            Testcases testcase = new Testcases();

            if (tcReq.getTestcaseId() != null) {
                testcase = testcasesRepository.findById(tcReq.getTestcaseId()).orElse(null);
                if (testcase == null) {
                    // create new if id not found
                    testcase = new Testcases();
                    String newId = CustomIdGenerator.generateNextId("TC", lastTcIdRef.get());
                    testcase.setTestcaseId(newId);
                    lastTcIdRef.set(newId);
                    testcase.setCreatedAt(LocalDateTime.now());
                } else {
                    testcase.setUpdatedAt(LocalDateTime.now());
                }
            } else {
                testcase = new Testcases();
                String newId = CustomIdGenerator.generateNextId("TC", lastTcIdRef.get());
                testcase.setTestcaseId(newId);
                lastTcIdRef.set(newId);
            }

            List<Object> inputs = deserializeInputs(tcReq.getInputs());

            if (inputs != null && !inputs.isEmpty() && request.getParameterNames() != null) {
                Map<String, Object> inputMap = new LinkedHashMap<>();
                List<String> parameterNames = request.getParameterNames();
                for (int i = 0; i < parameterNames.size() && i < inputs.size(); i++) {
                    inputMap.put(parameterNames.get(i), inputs.get(i));
                }
                testcase.setInputValues(toJson(inputMap));
            }

            if (tcReq.getOutput() != null) {
                testcase.setExpectedOutput(serializeExpectedOutput(tcReq.getOutput()));
            }

            testcase.setWeightage(tcReq.getWeightage());
            testcase.setTestcaseType(Testcases.TestcaseType.valueOf(tcReq.getTestcaseType().toUpperCase()));
            auditHelper.applyAuditForTestcase(testcase);
            testcase.setCodingQuestion(cq);

            updatedTestcases.add(testcase);
        }

        cq.getTestcases().addAll(updatedTestcases);
        codingQuestionsRepository.save(cq);

        return new ApiResponse("Coding question updated successfully", "success");
    }

    private String serializeExpectedOutput(Object expectedOutput) {
        try {
            return objectMapper.writeValueAsString(expectedOutput);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize expected output", e);
        }
    }

    private List<Object> deserializeInputs(String inputs) {
        try {
            if (inputs == null || inputs.trim().isEmpty()) {
                return new ArrayList<>();
            }
            return objectMapper.readValue(inputs, List.class);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public CodingQuestions getById(String id) {
        return codingQuestionsRepository.findById(id)
                .orElseThrow(() -> new CodingQuestionNotFoundException("Coding question not found with id: " + id));
    }

    @Override
    public CodingQuestionResponse getQuestionById(String id) {
        CodingQuestions cq = codingQuestionsRepository.findById(id)
            .orElseThrow(() -> new CodingQuestionNotFoundException("Coding question not found with id: " + id));

        CodingQuestionResponse dto = CodingQuestionMapper.toResponse(cq);

        dto.setParameterNames(deserializeList(cq.getInputParams()));
        dto.setInputTypes(deserializeList(cq.getInputType()));

        return dto;
    }


    @Override
    public ApiResponse deleteQuestion(String id) {
        CodingQuestions cq = codingQuestionsRepository.findById(id)
                .orElseThrow(() -> new CodingQuestionNotFoundException("Coding question not found with id: " + id));

        if (Boolean.FALSE.equals(cq.getIsActive())) {
            return new ApiResponse("Coding question already deleted", "failed");
        }

        cq.setIsActive(false);
        cq.setUpdatedAt(LocalDateTime.now());
        codingQuestionsRepository.save(cq);

        return new ApiResponse("Coding question deleted successfully", "success");
    }

    @Override
    public ApiResponse restoreQuestion(String id) {
        CodingQuestions cq = codingQuestionsRepository.findById(id)
                .orElseThrow(() -> new CodingQuestionNotFoundException("Coding question not found with id: " + id));

        if (Boolean.TRUE.equals(cq.getIsActive())) {
            return new ApiResponse("Coding question is already active", "failed");
        }

        cq.setIsActive(true);
        cq.setUpdatedAt(LocalDateTime.now());
        codingQuestionsRepository.save(cq);

        return new ApiResponse("Coding question restored successfully", "success");
    }
}
