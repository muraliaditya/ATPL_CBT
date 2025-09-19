package com.aaslin.cbt.developer.Dto;

import lombok.Data;

@Data
public class CompileRunRequestDto {
    private String userId;
    private String questionId;
    private String languageType;  
    private String code;
}

