package com.aaslin.cbt.super_admin.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aaslin.cbt.super_admin.dto.ApiResponse;
import com.aaslin.cbt.super_admin.dto.CodingQuestionRequestss;
import com.aaslin.cbt.super_admin.dto.PaginatedCodingQuestionResponse;
import com.aaslin.cbt.super_admin.service.CodingQuestionsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin/coding-questions")
@RequiredArgsConstructor
@Validated 
public class CodingQuestionController {

    private final CodingQuestionsService codingQuestionsService;

    
    @GetMapping("/search")
    public ResponseEntity<PaginatedCodingQuestionResponse> search(
            @RequestParam String question,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        PaginatedCodingQuestionResponse response = codingQuestionsService.search(question, page, size);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addQuestion(@RequestBody CodingQuestionRequestss request) {
        ApiResponse response = codingQuestionsService.addQuestion(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update/{questionId}")
    public ResponseEntity<ApiResponse> updateQuestion(
            @PathVariable String questionId,
            @RequestBody CodingQuestionRequestss request) {
        
        ApiResponse response = codingQuestionsService.updateQuestion(questionId, request);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/delete/{questionId}")
    public ResponseEntity<ApiResponse> deleteQuestion(@PathVariable String questionId) {
        ApiResponse response = codingQuestionsService.deleteQuestion(questionId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/restore/{questionId}")
    public ResponseEntity<ApiResponse> restoreQuestion(@PathVariable String questionId) {
        ApiResponse response = codingQuestionsService.restoreQuestion(questionId);
        return ResponseEntity.ok(response);
    }

}