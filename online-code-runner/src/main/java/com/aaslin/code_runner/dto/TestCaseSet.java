package com.aaslin.code_runner.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class TestCaseSet {
    private List<TestCaseResult> results;
    private int passedCount;

    public String getPassedCaseIndexes() {
        return results.stream()
                .filter(TestCaseResult::isPassed)
                .map(r -> String.valueOf(r.getIndex()))
                .collect(Collectors.joining(","));
    }
}