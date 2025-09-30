package com.aaslin.cbt.developer.service.Implementation;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aaslin.cbt.common.model.CodingQuestions;
import com.aaslin.cbt.common.model.Testcases;
import com.aaslin.cbt.common.model.User;
import com.aaslin.cbt.developer.Dto.AddCodingQuestionRequestDto;
import com.aaslin.cbt.developer.Dto.AddCodingQuestionResponse;
import com.aaslin.cbt.developer.Dto.AddTestcaseRequestDto;
import com.aaslin.cbt.developer.repository.CodingQuestionRepository;
import com.aaslin.cbt.developer.repository.TestcaseRepository;
import com.aaslin.cbt.developer.service.AddCodingQuestionService;
import com.aaslin.cbt.developer.util.CustomIdGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AddCodingQuestionServiceImpl implements AddCodingQuestionService {

    @Autowired
    private CodingQuestionRepository codingQuestionRepo;

    @Autowired
    private TestcaseRepository testcaseRepo;

    @Autowired
    private ObjectMapper objectMapper;

    @Transactional
    @Override
    public AddCodingQuestionResponse addCodingQuestion(AddCodingQuestionRequestDto request, String createdByUserId) {
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
        question.setApprovalStatus(CodingQuestions.ApprovalStatus.PENDING);
        question.setMethodName(request.getMethodName());
        question.setExecutionTimeLimit(request.getExecutionTimeLimit());
        question.setMemoryLimit(request.getMemoryLimit());
        question.setIsActive(true);
        
        LocalDateTime now = LocalDateTime.now();
        question.setCreatedAt(now);
        question.setUpdatedAt(now);
        question.setJavaBoilerCode(request.getJavaBoilerCode());
        question.setPythonBoilerCode(request.getPythonBoilerCode());

        try {
            question.setInputParams(objectMapper.writeValueAsString(Map.of("params", request.getInputParams())));
            question.setInputType(objectMapper.writeValueAsString(Map.of("type", request.getInputType())));
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize input params/type", e);
        }

        User user = new User();
        user.setUserId(createdByUserId);
        question.setCreatedBy(user);
        question.setUpdatedBy(user);

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
                // inputs to JSON
                Map<String, Object> inputWrapper = new LinkedHashMap<>();
                for (int i = 0; i < request.getInputParams().size(); i++) {
                    inputWrapper.put(request.getInputParams().get(i), tcDto.getInputValues().get(i));
                }
                tc.setInputValues(objectMapper.writeValueAsString(inputWrapper));

                // output to JSON
                tc.setExpectedOutput(objectMapper.writeValueAsString(Map.of("output", tcDto.getOutput())));
            } catch (Exception e) {
                throw new RuntimeException("Failed to serialize testcase data", e);
            }

            tc.setTestcaseType(Testcases.TestcaseType.valueOf(tcDto.getTestcaseType().toUpperCase()));
            tc.setWeightage(tcDto.getWeightage());
            tc.setDescription(tcDto.getDescription());
            tc.setCreatedAt(now);
            tc.setUpdatedAt(now);
            tc.setCreatedBy(user);
            tc.setUpdatedBy(user);

            testcaseRepo.save(tc);
        }

        return new AddCodingQuestionResponse("Coding question added successfully", "success");
    }
}