package com.aaslin.cbt.controller;

import com.aaslin.cbt.entity.MCQAnswers;
import com.aaslin.cbt.service.MCQAnswersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/answers")
public class MCQAnswersController {

    @Autowired
    private MCQAnswersService answersService;

    @GetMapping("/all")
    public List<MCQAnswers> getAllAnswers() {
        return answersService.getAllAnswers();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<MCQAnswers> getAnswerById(@PathVariable String id) {
        return ResponseEntity.of(answersService.getAnswerById(id));
    }

    @GetMapping("/mcqid/{mcqId}")
    public List<MCQAnswers> getAnswersByMcqId(@PathVariable String mcqId) {
        return answersService.getAnswersByMcqId(mcqId);
    }

    @PostMapping("/post")
    public ResponseEntity<MCQAnswers> createAnswer(@RequestBody MCQAnswers answer) {
        return ResponseEntity.status(HttpStatus.CREATED).body(answersService.createAnswer(answer));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MCQAnswers> updateAnswer(@PathVariable String id, @RequestBody MCQAnswers answer) {
        return ResponseEntity.of(answersService.updateAnswer(id, answer));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAnswer(@PathVariable String id) {
        answersService.deleteAnswer(id);
    }
}