package com.aaslin.cbt.developer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aaslin.cbt.developer.Dto.CodingQuestionResponse;
import com.aaslin.cbt.developer.service.CodingQuestionService;

@RestController
@RequestMapping("/api/v1/dev/coding-questions")
public class CodingQuestionController {

    @Autowired
    private CodingQuestionService questionService;

    @GetMapping("/search")
    public ResponseEntity<CodingQuestionResponse> searchCodingQuestions(
            @RequestParam(required = false) String question,
            @RequestParam(defaultValue = "1") int page) {
        return ResponseEntity.ok(questionService.searchQuestions(question, page));
    }
}
