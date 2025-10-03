package com.aaslin.cbt.super_admin.controller;
import com.aaslin.cbt.super_admin.dto.ApiResponse;
import com.aaslin.cbt.super_admin.dto.ContestDTO;
import com.aaslin.cbt.super_admin.dto.PaginatedContestResponse;
import com.aaslin.cbt.super_admin.service.ContestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/contests")
@RequiredArgsConstructor
public class ContestController {

    private final ContestService contestService;

    @PostMapping
    public ApiResponse createContest(@RequestBody ContestDTO dto) {
        String msg = contestService.createContest(dto);
        return new ApiResponse("Success", msg);
    }

    @PutMapping("/{contestId}")
    public ApiResponse updateContest(@PathVariable String contestId, @RequestBody ContestDTO dto) {
        String msg = contestService.updateContest(contestId, dto);
        return new ApiResponse("Success", msg);
    }

    @DeleteMapping("/{contestId}")
    public ApiResponse deleteContest(@PathVariable String contestId) {
        String msg = contestService.deleteContest(contestId);
        return new ApiResponse("Success", msg);
    }

    @GetMapping
    public List<ContestDTO> getAllContests() {
        return contestService.getAllContests();
    }
    
    @GetMapping("/{contestId}")
    public ContestDTO viewContest(@PathVariable String contestId) {
        return contestService.getContestById(contestId);
    }
    
    @GetMapping("/search")
    public PaginatedContestResponse searchContests(
            @RequestParam(required = false) String contestName,
            @RequestParam(required = false, defaultValue = "ALL") String status,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

    	return contestService.searchContests(contestName, status, page, size);
         
    }
}
