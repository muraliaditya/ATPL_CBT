package com.aaslin.cbt.developer.Dto;

import com.aaslin.cbt.common.model.CodingQuestion.Difficulty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class CodingQuestionDto {

    private String questionId;
    private String questionName;
    private Difficulty difficulty;

    public CodingQuestionDto(String questionId, String questionName, Difficulty difficulty) {
        this.questionId = questionId;
        this.questionName = questionName;
        this.difficulty = difficulty;
    }


}
