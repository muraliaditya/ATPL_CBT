package com.aaslin.cbt.super_admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aaslin.cbt.super_admin.dto.ResultResponseDTO;
import com.aaslin.cbt.super_admin.service.ResultService;

import lombok.RequiredArgsConstructor;

@RestController	
@RequestMapping("/api/admin/results")	
@RequiredArgsConstructor	
public class ResultController {
	    
	private final ResultService resultService;

	    @GetMapping("/{submissionId}")
	    public ResultResponseDTO getResult(
	            @PathVariable String submissionId,
	            @RequestParam String participantId,
	            @RequestParam String contestId) {
	        return resultService.getResult(submissionId, participantId, contestId);
	    }
	}



