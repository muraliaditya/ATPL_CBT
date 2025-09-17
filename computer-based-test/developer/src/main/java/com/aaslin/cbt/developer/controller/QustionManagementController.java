package com.aaslin.cbt.developer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aaslin.cbt.developer.Dto.AddCodingQuestionRequestDto;
import com.aaslin.cbt.developer.Dto.AddCodingQuestionResponse;
import com.aaslin.cbt.developer.Dto.CodingQuestionResponse;
import com.aaslin.cbt.developer.Dto.FetchCodingQuestionDto;
import com.aaslin.cbt.developer.Dto.RecentCodingQuestionResponse;
import com.aaslin.cbt.developer.service.AddCodingQuestionService;
import com.aaslin.cbt.developer.service.CodingQuestionService;
import com.aaslin.cbt.developer.service.FetchCodingQuestionService;
import com.aaslin.cbt.developer.service.RecentCodingQuestionService;

@RestController
@RequestMapping("/api/v1/dev/questions")        // http://localhost:8080/api/v1/dev/questions
public class QustionManagementController {

    @Autowired
    private CodingQuestionService questionService;

    @GetMapping("/search")
    public ResponseEntity<CodingQuestionResponse> searchCodingQuestions(
            @RequestParam(required = false) String question,
            @RequestParam(defaultValue = "1") int page) {
        return ResponseEntity.ok(questionService.searchQuestions(question, page));
    }
    
    @Autowired
    private RecentCodingQuestionService recentQuestionService;
    
    @GetMapping("/recent")
    public ResponseEntity<RecentCodingQuestionResponse> getRecentQuestions(){
    	return ResponseEntity.ok(recentQuestionService.getRecentQuestions());
    }
    
    @Autowired 
    private FetchCodingQuestionService fetchQuestionService;
    
    @GetMapping("/{questionId}")
    public ResponseEntity<FetchCodingQuestionDto> getCodingQuestionById(@PathVariable String questionId) {
        return ResponseEntity.ok(fetchQuestionService.getQuestionById(questionId));
    }
    
    @Autowired
    private AddCodingQuestionService addQuestionService;

    @PostMapping
    public ResponseEntity<AddCodingQuestionResponse> addQuestion(
            @RequestBody AddCodingQuestionRequestDto request) {
        try {
        	String userId = "DEV001";
            AddCodingQuestionResponse response = addQuestionService.addCodingQuestion(request, userId);
            return ResponseEntity.ok(response);  
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AddCodingQuestionResponse("Failed: " + e.getMessage(), "error"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AddCodingQuestionResponse("Server error: " + e.getMessage(), "error"));
        }
    }
    
}
