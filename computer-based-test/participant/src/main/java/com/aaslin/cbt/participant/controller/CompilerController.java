package com.aaslin.cbt.participant.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aaslin.cbt.participant.dto.CompileRunRequest;
import com.aaslin.cbt.participant.dto.CompileRunResponse;
import com.aaslin.cbt.participant.dto.SubmissionRequest;
import com.aaslin.cbt.participant.dto.SubmissionResponse;
import com.aaslin.cbt.participant.service.CompilerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/coding")
@RequiredArgsConstructor
public class CompilerController {

	private final CompilerService compilerService;
	
	@PostMapping("/compile-run")
	public CompileRunResponse compileRun(@RequestBody CompileRunRequest request) {
		return compilerService.compileAndRun(request);
		}
	
	@PostMapping("/submission")
	public SubmissionResponse submitCode(@RequestBody SubmissionRequest request) throws Exception {
		return compilerService.submitCode(request);
	}
}
