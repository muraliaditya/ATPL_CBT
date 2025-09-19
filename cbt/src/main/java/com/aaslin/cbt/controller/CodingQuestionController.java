package com.aaslin.cbt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aaslin.cbt.entity.CodingQuestion;
import com.aaslin.cbt.service.CodingQuestionService;

@RestController
@RequestMapping("/api/admin/coding")
public class CodingQuestionController {

    @Autowired
    private CodingQuestionService codingService;

    
    @GetMapping("/{contestId}")
    public ResponseEntity<List<CodingQuestion>> getAllByContest(@PathVariable String contestId) {
        List<CodingQuestion> questions = codingService.getByContestId(contestId);
        return ResponseEntity.ok(questions);
    }
    
    @PostMapping("/create/{contestId}")
    public ResponseEntity<CodingQuestion> create(@RequestBody CodingQuestion question, @PathVariable String contestId) {
        question.setContestId(contestId);
        CodingQuestion savedQuestion = codingService.createCodingQuestion(contestId, question);
        return ResponseEntity.ok(savedQuestion);
    }
    
    @PutMapping("/{codingQuestionId}")
    public ResponseEntity<CodingQuestion> edit(@PathVariable String codingQuestionId, @RequestBody CodingQuestion question) {
        CodingQuestion updatedQuestion = codingService.updateCoding(codingQuestionId, question);
        if (updatedQuestion == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedQuestion);
    }

    
    @DeleteMapping("/delete/{codingQuestionId}")
    public ResponseEntity<Void> delete(@PathVariable String codingQuestionId) {
        codingService.deleteCoding(codingQuestionId);
        return ResponseEntity.noContent().build();
    }
}
