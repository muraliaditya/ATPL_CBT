package com.aaslin.code_runner.controller;

import com.aaslin.code_runner.dto.CodeRequest;
import com.aaslin.code_runner.dto.CodeResponse;
import com.aaslin.code_runner.service.CodeRunnerService;
import com.aaslin.code_runner.RunRequest;
import com.aaslin.code_runner.RunResponse;

import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/code")
@RequiredArgsConstructor
public class CodeController {

    private final CodeRunnerService runnerService;

    @PostMapping("/run")
    public List<CodeResponse> runCode(@RequestBody CodeRequest request) {
        List<CodeResponse> results = new ArrayList<>();

        if (request.getTestCases() == null || request.getTestCases().isEmpty()) {
            throw new IllegalArgumentException("Test cases are required");
        }

        for (CodeRequest.TestCase tc : request.getTestCases()) {
            RunRequest runReq = new RunRequest();
            runReq.setLanguage(RunRequest.Language.fromString(request.getLanguage()));
            runReq.setCode(request.getCode());
            runReq.setStdin(tc.getInput());
            runReq.setTimeLimitSec(2);

            RunResponse runRes = runnerService.run(runReq);

            boolean passed = runRes.getOutput().trim().equals(tc.getExpectedOutput().trim());

            CodeResponse resp = new CodeResponse();
            resp.setInput(tc.getInput());
            resp.setExpectedOutput(tc.getExpectedOutput());
            resp.setActualOutput(runRes.getOutput().trim());
            resp.setError(runRes.getError());
            resp.setStatus(runRes.getStatus());
            resp.setPassed(passed);

            results.add(resp);
        }
        return results;
    }
}