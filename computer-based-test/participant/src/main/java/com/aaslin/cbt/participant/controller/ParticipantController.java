package com.aaslin.cbt.participant.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aaslin.cbt.participant.dto.ParticipantRequest;
import com.aaslin.cbt.participant.dto.ParticipantResponse;
import com.aaslin.cbt.participant.security.JwtUtil;
import com.aaslin.cbt.participant.service.ParticipantService;

@RestController
@RequestMapping("/api/v1/participants")
public class ParticipantController {

	private final ParticipantService participantService;

	public ParticipantController(ParticipantService participantService) {
		super();
		this.participantService = participantService;
	}
	
	@PostMapping
	public ResponseEntity<ParticipantResponse> register(@RequestParam String contestId,@RequestBody ParticipantRequest request){
		ParticipantResponse response=participantService.registerParticipant(contestId, request);
		String token=JwtUtil.generateToken(response.getParticipantId(), contestId);
		response.setToken(token);
		return ResponseEntity.ok(response);
	}
	
//	@PostMapping("/start")
//	public ResponseEntity<String> startTest(@RequestHeader("Authorization") String authHeader,@RequestParam String contestId){
//		String token=authHeader.replace("Bearer","");
//		String participantId=JwtUtil.validateTokenAndGetParticipantId(token);
//		return ResponseEntity.ok("Token valid ");
//	}
}
