package com.aaslin.cbt.super_admin.service;

import com.aaslin.cbt.common.model.CodingQuestion;
import com.aaslin.cbt.common.model.Testcase;
import com.aaslin.cbt.super_admin.dto.CodingQuestionResponse;
import com.aaslin.cbt.super_admin.dto.TestcaseResponse;

import java.util.List;
import java.util.stream.Collectors;

public class CodingQuestionMapper {

    public static CodingQuestionResponse toResponse(CodingQuestion cq) {
        CodingQuestionResponse dto = new CodingQuestionResponse();
        dto.setCodingQuestionId(cq.getCodingQuestionId());
        dto.setQuestion(cq.getQuestion());
        dto.setDescription(cq.getDescription());
        dto.setDifficulty(cq.getDifficulty() != null ? cq.getDifficulty().name() : null);
        dto.setOutputFormat(cq.getOutputFormat());
        dto.setMethodName(cq.getMethodName());
        dto.setJavaBoilerCode(cq.getJavaBoilerCode());
        dto.setPythonBoilerCode(cq.getPythonBoilerCode());
        dto.setExecutionTimeLimit(cq.getExecutionTimeLimit() != null ? cq.getExecutionTimeLimit().doubleValue() : null);
        dto.setMemoryLimit(cq.getMemoryLimit());
        dto.setInputTypes(List.of());      
        dto.setParameterNames(List.of());  
        dto.setIsActive(cq.getIsActive());
        dto.setWeightage(cq.getWeightage());

        if (cq.getTestcases() != null) {
            List<TestcaseResponse> tcs = cq.getTestcases().stream().map(tc -> {
                TestcaseResponse tr = new TestcaseResponse();
                tr.setTestcaseId(tc.getTestcaseId());
                tr.setInputValues(tc.getInputValues());
                tr.setExpectedOutput(tc.getExpectedOutput());
                tr.setTestcaseType(tc.getTestcaseType() != null ? tc.getTestcaseType().name() : null);
                tr.setWeightage(tc.getWeightage());
                tr.setDescription(tc.getDescription());
                return tr;
            }).collect(Collectors.toList());
            dto.setTestcases(tcs);
        }
        return dto;
    }
}
