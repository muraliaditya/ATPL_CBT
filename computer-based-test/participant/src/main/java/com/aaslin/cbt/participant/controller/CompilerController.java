package com.aaslin.cbt.participant.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aaslin.cbt.participant.dto.CompileRunRequest;
import com.aaslin.cbt.participant.dto.CompileRunResponse;
import com.aaslin.cbt.participant.dto.SubmissionRequest;
import com.aaslin.cbt.participant.dto.SubmissionResponse;
import com.aaslin.cbt.participant.service.CodingSubmissionService;
import com.aaslin.cbt.participant.service.CompilerService;

import lombok.RequiredArgsConstructor;

@RestController("participantCompilerController")
@RequestMapping("/api/v1/participant/coding")
@RequiredArgsConstructor
public class CompilerController {

	private final CompilerService compilerService;
	private final CodingSubmissionService submissionService;
	
	@PostMapping("/compile-run")
	public CompileRunResponse compileRun(@RequestBody CompileRunRequest request) {
		return compilerService.compileAndRun(request);
		}
	
	@PostMapping("/submit")
	public SubmissionResponse submitCode(@RequestBody SubmissionRequest request) throws Exception {
		SubmissionResponse response=compilerService.submitCode(request);
		submissionService.saveCodingSubmission(request, response);
		return response;
	}
}
