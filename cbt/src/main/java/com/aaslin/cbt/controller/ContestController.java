package com.aaslin.cbt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aaslin.cbt.entity.Contest;
import com.aaslin.cbt.service.ContestService;

@RestController
@RequestMapping("/api/contest")
public class ContestController {

    @Autowired
    private ContestService contestService;
    
    @GetMapping
    public ResponseEntity<List<Contest>> getAll() {
        return ResponseEntity.ok(contestService.getAllContests());
    }
    
    @GetMapping("/{contestId}")
    public ResponseEntity<Contest> getById(@PathVariable String contestId) {
        Contest contest = contestService.getContestById(contestId);
        if (contest == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(contest);
    }
    
    @PostMapping("/create")
    public ResponseEntity<Contest> create(@RequestBody Contest contest) {
        Contest savedContest = contestService.createContest(contest);
        return ResponseEntity.ok(savedContest);
    }
    
    @PutMapping("/update/{contestId}")
    public ResponseEntity<Contest> update(@PathVariable String contestId, @RequestBody Contest contest) {
        Contest updatedContest = contestService.updateContest(contestId, contest);
        if (updatedContest == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedContest);
    }
    
    @DeleteMapping("/delete/{contestId}")
    public ResponseEntity<Void> delete(@PathVariable String contestId) {
        contestService.deleteContest(contestId);
        return ResponseEntity.noContent().build();
    }
}

