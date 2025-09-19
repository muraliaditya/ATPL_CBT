package com.aaslin.cbt.super_admin.controller;

import com.aaslin.cbt.super_admin.dto.GenerateCodingQuestionsRequest;
import com.aaslin.cbt.super_admin.dto.GeneratedCodingQuestionResponse;
import com.aaslin.cbt.super_admin.service.CodingQuestionGenerationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/coding")
@RequiredArgsConstructor
public class CodingQuestionGenerationController {

    private final CodingQuestionGenerationService generationService;

    @PostMapping("/generate")
    public ResponseEntity<List<GeneratedCodingQuestionResponse>> generate(@RequestBody GenerateCodingQuestionsRequest request) {
        List<GeneratedCodingQuestionResponse> result = generationService.generateQuestions(request);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/generate")
    public ResponseEntity<GeneratedCodingQuestionResponse> regenerate(
            @RequestParam("preference") String preference,
            @RequestParam("questionid") String questionId) {
        GeneratedCodingQuestionResponse result = generationService.regenerateQuestion(preference, questionId);
        return ResponseEntity.ok(result);
    }
}
