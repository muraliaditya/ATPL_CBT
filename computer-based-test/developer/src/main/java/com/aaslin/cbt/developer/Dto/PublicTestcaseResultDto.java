package com.aaslin.cbt.developer.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PublicTestcaseResultDto {
    private String testcaseId;
    private String inputValues;
    private String expectedOutput;
    private String actualOutput;
    private String testcaseStatus;
    private int weightage;
}
