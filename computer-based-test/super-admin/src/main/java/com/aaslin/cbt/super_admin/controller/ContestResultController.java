package com.aaslin.cbt.super_admin.controller;
import lombok.RequiredArgsConstructor;

import java.io.ByteArrayInputStream;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.aaslin.cbt.super_admin.dto.ContestResultsResponse;
import com.aaslin.cbt.super_admin.service.ContestResultService;


@RestController
@RequestMapping("/api/v1/admin/contests")
@RequiredArgsConstructor
public class ContestResultController {

    private final ContestResultService contestResultService;

    @GetMapping("/results/{contestId}")
    public ContestResultsResponse getContestResults(@PathVariable String contestId) {
        return contestResultService.getContestResults(contestId);
    }
    @GetMapping("/results/{contestId}/download")
    public ResponseEntity<InputStreamResource> downloadResultsExcel(@PathVariable String contestId) {
        ByteArrayInputStream in = contestResultService.generateExcel(contestId);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=contest_results_" + contestId + ".xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(in));
    }

}
