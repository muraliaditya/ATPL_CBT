package com.aaslin.cbt.developer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aaslin.cbt.developer.Dto.AddCodingQuestionRequestDto;
import com.aaslin.cbt.developer.Dto.AddCodingQuestionResponse;
import com.aaslin.cbt.developer.Dto.AddMcqQuestionRequestDto;
import com.aaslin.cbt.developer.Dto.AddMcqQuestionResponse;
import com.aaslin.cbt.developer.Dto.CodingQuestionResponse;
import com.aaslin.cbt.developer.Dto.FetchCodingQuestionDto;
import com.aaslin.cbt.developer.Dto.RecentCodingQuestionResponse;
import com.aaslin.cbt.developer.service.AddCodingQuestionService;
import com.aaslin.cbt.developer.service.AddMcqQuestionService;
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
            @RequestParam(required = false) String difficulty,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(questionService.searchQuestions(question,difficulty, page, size));
    }
    
    @Autowired
    private RecentCodingQuestionService recentQuestionService;
    
    @GetMapping("/recent")
    public ResponseEntity<RecentCodingQuestionResponse> getRecentQuestions(
            @RequestParam(required = false) Integer limit) {
        return ResponseEntity.ok(recentQuestionService.getRecentQuestions(limit));
    }
    
    @Autowired 
    private FetchCodingQuestionService fetchQuestionService;
    
    @GetMapping("/{questionId}")
    public ResponseEntity<FetchCodingQuestionDto> getCodingQuestionById(@PathVariable String questionId) {
        return ResponseEntity.ok(fetchQuestionService.getQuestionById(questionId));
    }
    
    @Autowired
    private AddCodingQuestionService codingQuestionService;

    @PostMapping("/coding-questions")
    public ResponseEntity<AddCodingQuestionResponse> addQuestion(
            @RequestBody AddCodingQuestionRequestDto request) {
        try {
        	AddCodingQuestionResponse response = codingQuestionService.addCodingQuestion(request);
            return ResponseEntity.ok(response);  
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AddCodingQuestionResponse("Failed: " + e.getMessage(), "error"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AddCodingQuestionResponse("Server error: " + e.getMessage(), "error"));
        }
    }
    
    @Autowired 
    private AddMcqQuestionService mcqQuestionService;
    
    @PostMapping("/mcq-questions")
    public ResponseEntity<AddMcqQuestionResponse> addMcqQuestions(
            @RequestBody AddMcqQuestionRequestDto request) {
    	try {
    		AddMcqQuestionResponse response = mcqQuestionService.addMcqQuestions(request);
    		return ResponseEntity.ok(response);
    	} catch(IllegalArgumentException e) {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    				.body(new AddMcqQuestionResponse("Failed: "+e.getMessage(),"error"));
    	} catch(Exception e) {
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AddMcqQuestionResponse("Server error: " + e.getMessage(), "error"));
    	}
    }
    
}
