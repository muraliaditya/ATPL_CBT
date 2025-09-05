package com.aaslin.cbt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aaslin.cbt.entity.Contest;
import com.aaslin.cbt.entity.MCQQuestion;
import com.aaslin.cbt.service.ContestService;
import com.aaslin.cbt.service.MCQQuestionService;

@RestController
@RequestMapping("/api/admin/mcqs")
public class MCQQuestionController {

    @Autowired
    private MCQQuestionService mcqService;
    @Autowired
    private ContestService contestservice;


    @GetMapping("/{contestId}")
    public ResponseEntity<List<MCQQuestion>> getAllByContest(@PathVariable String contestId) {
        List<MCQQuestion> mcqs = mcqService.getMcqsByContest(contestId);
        return ResponseEntity.ok(mcqs);
    }
    
    @PostMapping("/create/{contestId}")
    public MCQQuestion create(@RequestBody MCQQuestion q, @PathVariable String contestId) {
        Contest contest = contestservice.getContestById(contestId);
        if (contest == null) {
            throw new RuntimeException("Contest not found with id: " + contestId);
        }
        q.setContest(contest);  
        return mcqService.createMcq(contestId, q);
    }
    
    @PutMapping("/{mcqId}")
    public ResponseEntity<MCQQuestion> edit(@PathVariable String mcqId, @RequestBody MCQQuestion mcq) {
        MCQQuestion updatedMcq = mcqService.updateMcq(mcqId, mcq);
        if (updatedMcq == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedMcq);
    }
    
    @DeleteMapping("/delete/{mcqId}")
    public ResponseEntity<Void> delete(@PathVariable String mcqId) {
        mcqService.deleteMcq(mcqId);
        return ResponseEntity.noContent().build();
    }
}
