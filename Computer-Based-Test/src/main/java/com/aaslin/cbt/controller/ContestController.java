package com.aaslin.cbt.controller;

import com.aaslin.cbt.dto.ContestInfoResponseDto;
import com.aaslin.cbt.service.ContestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/contests")
public class ContestController {

    private final ContestService contestService;

    public ContestController(ContestService contestService) {
        this.contestService = contestService;
    }

    @GetMapping("/getInfo/{contestId}")
    public ResponseEntity<ContestInfoResponseDto> getContestDetails(@PathVariable String contestId) {
        return ResponseEntity.ok(contestService.getContestDetails(contestId));
    }
}

