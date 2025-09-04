package com.aaslin.code_runner.service;

import com.aaslin.code_runner.RunRequest;
import com.aaslin.code_runner.RunResponse;

public interface CodeRunnerService {
    RunResponse run(RunRequest request);
}