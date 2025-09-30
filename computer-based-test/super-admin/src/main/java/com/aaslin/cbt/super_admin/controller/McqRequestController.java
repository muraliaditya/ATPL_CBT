package com.aaslin.cbt.super_admin.controller;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aaslin.cbt.super_admin.dto.McqQuestionDTO;
import com.aaslin.cbt.super_admin.dto.McqRequestDTO;
import com.aaslin.cbt.super_admin.service.McqRequestService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class McqRequestController {

    private final McqRequestService mcqService;
    @GetMapping("/mcq-requests")
    public List<McqRequestDTO> getAllRequests(@RequestParam(required = false) String username) {
        return mcqService.getAllRequests(username);
    }

    @PostMapping("/mcq-requests/{mcqId}")
    public ResponseEntity<McqQuestionDTO> approveRequest(
            @PathVariable String mcqId,
            @RequestParam String action,
            @RequestParam String approverId) {

        McqQuestionDTO updated = mcqService.updateApprovalStatus(mcqId, action, approverId);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/details")
    public McqQuestionDTO getMcqDetails(@RequestParam String mcqId) {
    	return mcqService.getMcqDetailsById(mcqId);
        }
    
}
