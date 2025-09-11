package com.cbt.mcq_test.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cbt.mcq_test.Service.ContestMcqMapService;
import com.cbt.mcq_test.entity.ContestMcqMap;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/contest/mcq")
@RequiredArgsConstructor
public class ContestMcqMapController {

    private final ContestMcqMapService mapService;
  
    @PostMapping("/assign")
    public ResponseEntity<ContestMcqMap> assignQuestion(
            @RequestParam String contestId,
            @RequestParam String mcqId,
            @RequestParam(defaultValue = "1") int weightage) {
        
        ContestMcqMap mapping = mapService.assignQuestionToContest(contestId, mcqId, weightage);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapping);
    }

    @GetMapping("/contest/{contestId}")
    public ResponseEntity<List<ContestMcqMap>> getQuestionsForContest(@PathVariable String contestId) {
        return ResponseEntity.ok(mapService.getQuestionsForContest(contestId));
    }
}






