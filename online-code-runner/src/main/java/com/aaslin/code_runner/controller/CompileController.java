package com.aaslin.code_runner.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aaslin.code_runner.RunRequest;
import com.aaslin.code_runner.RunResponse;
import com.aaslin.code_runner.service.DockerRunnerService;

@RestController
@RequestMapping("/api/run")
public class CompileController {

	private final DockerRunnerService runner;

	public CompileController(DockerRunnerService runner) {
		super();
		this.runner = runner;
	}
	
	@PostMapping(consumes=MediaType.APPLICATION_JSON_VALUE,produces=MediaType.APPLICATION_JSON_VALUE)
	public RunResponse run(@RequestBody RunRequest request) {
		return runner.run(request);
	}
	
}
