	package com.aaslin.cbt.participant.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aaslin.cbt.participant.dto.ContestDetailsResponse;
import com.aaslin.cbt.participant.dto.ContestEligibilityResponse;
import com.aaslin.cbt.participant.dto.ContestStartResponse;
import com.aaslin.cbt.participant.dto.TestSubmissionRequest;
import com.aaslin.cbt.participant.dto.TestSubmissionResponse;
import com.aaslin.cbt.participant.security.JwtUtil;
import com.aaslin.cbt.participant.service.ContestService;

@RestController("participantContestController")
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
	
	@GetMapping("/{contestId}/basic-info")
	public ContestDetailsResponse contestDetails(@PathVariable String contestId) {
		return service.getContestInfo(contestId);
	}
	
	@GetMapping("/{contestId}/start")
	public ResponseEntity<ContestStartResponse> startContest(@PathVariable String contestId){
//	String token=authHeader.replace("Bearer","");
//	String participantId=JwtUtil.validateTokenAndGetParticipantId(token);,@RequestHeader("Authorization") String authHeader
	ContestStartResponse response=service.startContest(contestId);
	return ResponseEntity.ok(response);
	}
	
	@PostMapping("/{contestId}/submit")
	public ResponseEntity<TestSubmissionResponse> submitTest(@PathVariable String contestId,@RequestBody TestSubmissionRequest request){
		TestSubmissionResponse response=service.saveTestSubmission(request);
		return ResponseEntity.ok(response);
	}
}

