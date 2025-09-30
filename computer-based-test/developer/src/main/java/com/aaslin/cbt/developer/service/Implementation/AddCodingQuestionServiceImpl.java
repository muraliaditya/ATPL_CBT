package com.aaslin.cbt.developer.service.Implementation;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aaslin.cbt.common.model.CodingQuestions;
import com.aaslin.cbt.common.model.Testcases;
import com.aaslin.cbt.developer.Dto.AddCodingQuestionRequestDto;
import com.aaslin.cbt.developer.Dto.AddCodingQuestionResponse;
import com.aaslin.cbt.developer.Dto.AddTestcaseRequestDto;
import com.aaslin.cbt.developer.repository.CodingQuestionRepository;
import com.aaslin.cbt.developer.repository.TestcaseRepository;
import com.aaslin.cbt.developer.service.AddCodingQuestionService;
import com.aaslin.cbt.developer.util.CustomIdGenerator;
import com.aaslin.cbt.super_admin.util.AuditHelper;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddCodingQuestionServiceImpl implements AddCodingQuestionService {

    private CodingQuestionRepository codingQuestionRepo;
    private TestcaseRepository testcaseRepo;
    private ObjectMapper objectMapper;
    private AuditHelper auditHelper;

    @Transactional
    @Override
    public AddCodingQuestionResponse addCodingQuestion(AddCodingQuestionRequestDto request) {
        if (request.getTestcases() == null || request.getTestcases().isEmpty()) {
            throw new IllegalArgumentException("At least one testcase is required");
        }

        String lastQuestionId = codingQuestionRepo.findTopByOrderByCodingQuestionIdDesc()
                .map(CodingQuestions::getCodingQuestionId)
                .orElse(null);
        String newQuestionId = CustomIdGenerator.generateNextId("CQ", lastQuestionId);

        CodingQuestions question = new CodingQuestions();
        question.setCodingQuestionId(newQuestionId);
        question.setQuestion(request.getQuestion());
        question.setDescription(request.getDescription());
        question.setDifficulty(CodingQuestions.Difficulty.valueOf(request.getDifficulty().toUpperCase()));
        question.setMethodName(request.getMethodName());
        question.setExecutionTimeLimit(request.getExecutionTimeLimit());
        question.setMemoryLimit(request.getMemoryLimit());
        question.setIsActive(true);
        question.setWeightage(request.getWeightage());
        question.setOutputFormat(request.getOutputFormat());
        question.setJavaBoilerCode(request.getJavaBoilerCode());
        question.setPythonBoilerCode(request.getPythonBoilerCode());

        try {
            //Store param types as {"nums":"int[]","target":"int"}
            Map<String, String> typeMap = new LinkedHashMap<>();
            for (int i = 0; i < request.getInputParams().size(); i++) {
                typeMap.put(request.getInputParams().get(i), request.getInputType().get(i));
            }
            question.setInputType(objectMapper.writeValueAsString(typeMap));

            //Store param names list as {"params":["nums","target"]}
            question.setInputParams(objectMapper.writeValueAsString(Map.of("params", request.getInputParams())));
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize input params/type", e);
        }
        
        auditHelper.applyAuditForCodingQuestion(question);

        codingQuestionRepo.save(question);

        for (AddTestcaseRequestDto tcDto : request.getTestcases()) {
            if (tcDto.getInputValues() == null || tcDto.getInputValues().isEmpty()) {
                throw new IllegalArgumentException("Testcase inputs cannot be null or empty");
            }

            String lastTestcaseId = testcaseRepo.findTopByOrderByTestcaseIdDesc()
                    .map(Testcases::getTestcaseId)
                    .orElse(null);
            String newTestcaseId = CustomIdGenerator.generateNextId("TC", lastTestcaseId);

            Testcases tc = new Testcases();
            tc.setTestcaseId(newTestcaseId);
            tc.setCodingQuestion(question);

            try {
                // Store actual input values as {"nums":[2,7,11,15],"target":9}
                Map<String, Object> inputWrapper = new LinkedHashMap<>();
                for (int i = 0; i < request.getInputParams().size(); i++) {
                    inputWrapper.put(request.getInputParams().get(i), tcDto.getInputValues().get(i));
                }
                tc.setInputValues(objectMapper.writeValueAsString(inputWrapper));
            } catch (Exception e) {
                throw new RuntimeException("Failed to serialize testcase data", e);
            }

            tc.setTestcaseType(Testcases.TestcaseType.valueOf(tcDto.getTestcaseType().toUpperCase()));
            tc.setWeightage(tcDto.getWeightage());
            tc.setDescription(tcDto.getDescription());
            tc.setExpectedOutput(tcDto.getExpectedOutput());
            auditHelper.applyAuditForTestcase(tc);

            testcaseRepo.save(tc);
        }

        return new AddCodingQuestionResponse("Coding question added successfully", "success");
    }
}