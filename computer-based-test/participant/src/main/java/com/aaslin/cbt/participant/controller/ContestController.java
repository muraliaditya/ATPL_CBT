package com.aaslin.cbt.participant.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aaslin.cbt.participant.dto.ContestEligibilityResponse;
import com.aaslin.cbt.participant.service.ContestService;

@RestController
@RequestMapping("/api/v1/contests")
public class ContestController {

	private final ContestService service;

	public ContestController(ContestService service) {
		super();
		this.service = service;
	}
	
	@GetMapping("/{contestId}/eligibility")
	public ContestEligibilityResponse getEligiblity(@PathVariable String contestId) {
		return service.checkEligibility(contestId);
	}
	
}

