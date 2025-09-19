package com.aaslin.cbt.developer.Dto;

import java.util.List;

import lombok.Data;

@Data
public class AddMcqQuestionRequestDto {
    private List<McqQuestionDto> mcqQuestions;

    @Data
    public static class McqQuestionDto {
        private String question;
        private String option1;
        private String option2;
        private String option3;
        private String option4;
        private String answerKey;
        private Integer weightage;
        private String section; // This will map to Sections.section
        private Boolean isActive;
    }
}
