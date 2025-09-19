package com.aaslin.cbt.developer.controller;

import com.aaslin.cbt.developer.Dto.*;
import com.aaslin.cbt.developer.service.CompilerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/dev/questions")
@RequiredArgsConstructor
public class CompilerController {

    private final CompilerService compilerService;

    @PostMapping("/compile-run")
    public ResponseEntity<CompileRunResponseDto> compileRun(@RequestBody CompileRunRequestDto request) {
        CompileRunResponseDto response = compilerService.compileRun(
        		request.getUserId(),
        		request.getQuestionId(),
                request.getLanguageType(),
                request.getCode()
        );
        return ResponseEntity.ok(response);
    }

   
    @PostMapping("/submit")
    public ResponseEntity<SubmitResponseDto> submit(@RequestBody CompileRunRequestDto request) {
        SubmitResponseDto response = compilerService.submit(
                request.getUserId(),
                request.getQuestionId(),
                request.getLanguageType(),
                request.getCode()
        );
        return ResponseEntity.ok(response);
    }
}