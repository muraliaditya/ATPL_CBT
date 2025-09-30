package com.aaslin.cbt.developer.service.Implementation;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aaslin.cbt.common.model.CodingQuestions;
import java.util.List;
import com.aaslin.cbt.common.model.Testcases;
import com.aaslin.cbt.common.model.User;
import com.aaslin.cbt.developer.Dto.AddCodingQuestionRequestDto;
import com.aaslin.cbt.developer.Dto.AddCodingQuestionResponse;
import com.aaslin.cbt.developer.Dto.AddTestcaseRequestDto;
import com.aaslin.cbt.developer.repository.CodingQuestionRepository;
import com.aaslin.cbt.developer.repository.TestcaseRepository;
import com.aaslin.cbt.developer.repository.UserRepository;
import com.aaslin.cbt.developer.service.AddCodingQuestionService;
import com.aaslin.cbt.developer.util.CustomIdGenerator;
import com.aaslin.cbt.developer.util.GetCurrentUsername;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddCodingQuestionServiceImpl implements AddCodingQuestionService {
	
    private CodingQuestionRepository codingQuestionRepo;
    private TestcaseRepository testcaseRepo;
    private UserRepository userRepository;
    private GetCurrentUsername currentUser;
    private ObjectMapper objectMapper;

    @Transactional
    @Override
    public AddCodingQuestionResponse addCodingQuestion(AddCodingQuestionRequestDto request) {
    	 if (request.getTestcases() == null || request.getTestcases().isEmpty()) {
             throw new IllegalArgumentException("At least one testcase is required");
         }

         String username = currentUser.getCurrentUsername();
         if (username == null) throw new RuntimeException("Unauthorized: user not found in context");

         User user = userRepository.findByUsername(username)
                 .orElseThrow(() -> new RuntimeException("User not found"));

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
         question.setCreatedBy(user);
         question.setUpdatedBy(user);

         LocalDateTime now = LocalDateTime.now();
         question.setCreatedAt(now);
         question.setUpdatedAt(now);
         question.setJavaBoilerCode(request.getJavaBoilerCode());
         question.setPythonBoilerCode(request.getPythonBoilerCode());

         try {
             // Convert frontend strings to proper JSON objects for DB
             question.setInputParams(objectMapper.writeValueAsString(
                 Map.of("params", objectMapper.readValue(request.getInputParams(), List.class))
             ));
             question.setInputType(objectMapper.writeValueAsString(
                 Map.of("types", objectMapper.readValue(request.getInputType(), List.class))
             ));
         } catch (Exception e) {
             throw new RuntimeException("Failed to serialize input params/type", e);
         }

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
                 // inputValues stored as {"values":[[2,7,11,15],9]}
                 tc.setInputValues(objectMapper.writeValueAsString(
                     Map.of("values", objectMapper.readValue(tcDto.getInputValues(), List.class))
                 ));
                 // expectedOutput stored as plain varchar
                 tc.setExpectedOutput(tcDto.getExpectedOutput());
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