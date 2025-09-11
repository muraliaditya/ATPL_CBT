package com.aaslin.cbt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
//@RequestMapping("/api/auth")
//public class AuthController {
//	@Autowired
//	private CandidateService candidateService;
//	
//	// Shared login endpoint for both admin and candidate
//	@PostMapping("/login")
//	public ResponseEntity<ApiResponse<LoginResponseDTO>> login(@Valid @RequestBody UserLoginDTO loginDTO) {
//		ApiResponse<LoginResponseDTO> response = candidateService.login(loginDTO);
//		return ResponseEntity.ok(response);
//	}
//}
