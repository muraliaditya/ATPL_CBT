package com.aaslin.cbt.super_admin.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.aaslin.cbt.super_admin.dto.ContestResultsResponse;
import com.aaslin.cbt.super_admin.service.ContestResultService;


@RestController
@RequestMapping("/api/admin/contests")
@RequiredArgsConstructor
public class ContestResultController {

    private final ContestResultService contestResultService;

    @GetMapping("/results/{contestId}")
    public ContestResultsResponse getContestResults(@PathVariable String contestId) {
        return contestResultService.getContestResults(contestId);
    }
    

}
