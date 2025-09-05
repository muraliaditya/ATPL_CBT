package com.aaslin.cbt.controller;

import com.aaslin.cbt.entity.MCQQuestion;
import com.aaslin.cbt.service.MCQQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class MCQQuestionController {

    @Autowired
    private MCQQuestionService questionService;

    @GetMapping("/all")
    public List<MCQQuestion> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<MCQQuestion> getQuestionById(@PathVariable String id) {
        return ResponseEntity.of(questionService.getQuestionById(id));
    }

    @PostMapping("/post")
    public ResponseEntity<MCQQuestion> createQuestion(@RequestBody MCQQuestion question) {
        return ResponseEntity.status(HttpStatus.CREATED).body(questionService.createQuestion(question));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MCQQuestion> updateQuestion(@PathVariable String id, @RequestBody MCQQuestion question) {
        return ResponseEntity.of(questionService.updateQuestion(id, question));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteQuestion(@PathVariable String id) {
        questionService.deleteQuestion(id);
    }
}