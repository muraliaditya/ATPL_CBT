package com.aaslin.cbt.super_admin.controller;

import com.aaslin.cbt.super_admin.dto.*;
import com.aaslin.cbt.super_admin.service.ContestCodingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/contests/coding")
@RequiredArgsConstructor
public class ContestCodingController {

    private final ContestCodingService contestCodingService;

    @PostMapping("/assign")
    public ResponseEntity<ApiResponse> assignQuestions(@RequestBody AssignCodingQuestionsRequest request) {
        return ResponseEntity.ok(contestCodingService.assignQuestions(request));
    }
    
    @PostMapping("/{contestId}/auto-assign")
    public ResponseEntity<ApiResponse> autoAssign(
            @PathVariable String contestId,
            @RequestBody GenerateCodingQuestionsRequest request) {
        return ResponseEntity.ok(contestCodingService.autoAssignQuestions(contestId, request));
    }


    @DeleteMapping("/{contestId}/remove/{codingQuestionId}")
    public ResponseEntity<ApiResponse> removeQuestion(
            @PathVariable String contestId,
            @PathVariable String codingQuestionId) {
        return ResponseEntity.ok(contestCodingService.removeQuestion(contestId, codingQuestionId));
    }

    @GetMapping("/{contestId}/questions")
    public ResponseEntity<List<ContestCodingResponse>> getQuestions(@PathVariable String contestId) {
        return ResponseEntity.ok(contestCodingService.getQuestionsByContest(contestId));
    }
}
