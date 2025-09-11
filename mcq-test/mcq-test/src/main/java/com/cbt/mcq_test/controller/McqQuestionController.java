package com.cbt.mcq_test.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cbt.mcq_test.Service.McqQuestionService;
import com.cbt.mcq_test.entity.McqQuestion;

import java.util.List;

@RestController
@RequestMapping("/api/mcqs")
public class McqQuestionController {

    private final McqQuestionService mcqService;

    public McqQuestionController(McqQuestionService mcqService) {
        this.mcqService = mcqService;
    }
    
    @GetMapping("/section")
    public ResponseEntity<List<McqQuestion>> getMcqQuestions(
            @RequestParam(value = "section", required = false) McqQuestion.Section section) {

        List<McqQuestion> questions = 
            (section == null) ? mcqService.getAllMcqs() : mcqService.getQuestionsBySection(section);

        return ResponseEntity.ok(questions);
    }
    
    @PostMapping
    public ResponseEntity<McqQuestion> addQuestion(@RequestBody McqQuestion mcq) {
        McqQuestion saved = mcqService.addQuestion(mcq);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    
    @PostMapping("/add")
    public ResponseEntity<List<McqQuestion>> addQuestions(@RequestBody List<McqQuestion> mcqs) {
        List<McqQuestion> saved = mcqService.addQuestions(mcqs);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }


    @PutMapping("/{id}")
    public ResponseEntity<McqQuestion> updateMcq(@PathVariable String id, @RequestBody McqQuestion mcq) {
        return ResponseEntity.ok(mcqService.updateMcq(id, mcq));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMcq(@PathVariable String id) {
        mcqService.deleteMcq(id);
        return ResponseEntity.noContent().build();
    }
}
