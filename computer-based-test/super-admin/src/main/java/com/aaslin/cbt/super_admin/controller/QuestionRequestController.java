package com.aaslin.cbt.super_admin.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aaslin.cbt.super_admin.dto.QuestionRequestDTO;
import com.aaslin.cbt.super_admin.service.QuestionRequestService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class QuestionRequestController {

    private final QuestionRequestService questionService;

    @GetMapping("/requests")
    public List<QuestionRequestDTO> getAllRequests(@RequestParam(required = false) String username) {
        return questionService.getAllRequests(username);
    }

    @PostMapping("/requests/{questionId}")
    public ResponseEntity<Object> approveRequest(
        @PathVariable String questionId,
        @RequestParam String action,
        @RequestParam String approverId,
        @RequestParam String type) {

        Object updated = questionService.updateApprovalStatus(questionId, action, approverId, type);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/mcqdetails")
    public ResponseEntity<Object> getDetails(@RequestParam String mcqId) {
        Object details = questionService.getMcqDetailsById(mcqId);
        return ResponseEntity.ok(details);
    }
    @GetMapping("/codingdetails")
    public ResponseEntity<Object> getCodingDetails(@RequestParam String CodingQuestionId){
    	Object details = questionService.getCodingDetailsById(CodingQuestionId);
		return ResponseEntity.ok(details);
    	
    }
}

