package com.cbt.mcq_test.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cbt.mcq_test.Service.ContestService;
import com.cbt.mcq_test.entity.Contest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/contests")
@RequiredArgsConstructor
public class ContestController {

    private final ContestService contestService;

    
    @PostMapping
    public ResponseEntity<Contest> createContest(@RequestBody Contest contest) {
        Contest saved = contestService.createContest(contest);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}

