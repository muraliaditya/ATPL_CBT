package com.aaslin.code_runner.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestCaseResults {
    private List<TestCaseResult> results;
    private int passedCount;
    private String passedCaseIndexes;
}
