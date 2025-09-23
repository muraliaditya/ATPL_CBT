package com.aaslin.cbt.super_admin.controller;
import com.aaslin.cbt.super_admin.dto.ContestDTO;
import com.aaslin.cbt.super_admin.dto.ContestResponseDTO;
import com.aaslin.cbt.super_admin.service.ContestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/contests")
@RequiredArgsConstructor
public class ContestController {

    private final ContestService contestService;

    @PostMapping
    public ContestResponseDTO createContest(@RequestBody ContestDTO dto) {
        String msg = contestService.createContest(dto);
        return new ContestResponseDTO("Success", msg);
    }

    @PutMapping("/{contestId}")
    public ContestResponseDTO updateContest(@PathVariable String contestId, @RequestBody ContestDTO dto) {
        String msg = contestService.updateContest(contestId, dto);
        return new ContestResponseDTO("Success", msg);
    }

    @DeleteMapping("/{contestId}")
    public ContestResponseDTO deleteContest(@PathVariable String contestId) {
        String msg = contestService.deleteContest(contestId);
        return new ContestResponseDTO("Success", msg);
    }

    @GetMapping
    public List<ContestDTO> getAllContests() {
        return contestService.getAllContests();
    }

    @GetMapping("/{contestId}")
    public ContestDTO viewContest(@PathVariable String contestId) {
        return contestService.getContestById(contestId);
    }
}
