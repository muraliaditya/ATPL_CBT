package com.aaslin.cbt.super_admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class McqQuestionAnswerDTO {
    private String mcqQuestionId;
    private String questionText;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String answerKey;
    private String selectedAnswer;
    private Boolean isCorrect;
    private Integer weightage;
    private String section; 
}
