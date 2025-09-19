package com.aaslin.cbt.super_admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodingQuestionSearchResponse {
    private String questionId;
    private String questionName;
    private String difficulty;
}