package com.aaslin.code_runner.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aaslin.code_runner.dto.SubmissionRequest;
import com.aaslin.code_runner.dto.SubmissionResponse;
import com.aaslin.code_runner.service.SubmissionService;

@RestController
@RequestMapping("/api/submit")
public class SubmissionController {

	
	private SubmissionService service;

	public SubmissionController(SubmissionService service) {
		super();
		this.service = service;
	}
	
	@PostMapping
	public SubmissionResponse submit(@RequestBody SubmissionRequest req) {
		return service.submitCode(req);
	}
}
