package com.aaslin.code_runner.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TestCaseResult {
    private int index;
    private String actualOutput;
    private String expectedOutput;
    private boolean passed;
    private int marksAwarded;
}