package com.aaslin.cbt.developer.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PrivateTestcaseResultDto {
    private String testcaseId;
    private String testcaseStatus;
    private int weightage;
}