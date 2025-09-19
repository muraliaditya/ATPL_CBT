package com.aaslin.cbt.developer.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class CompileRunResponseDto {
    private String codeStatus;
    private String message;
    private int publicTestcasesPassed;
    private List<PublicTestcaseResultDto> publicTestcaseResults;

    public CompileRunResponseDto(String codeStatus, String message) {
        this.codeStatus = codeStatus;
        this.message = message;
        this.publicTestcasesPassed = 0;
        this.publicTestcaseResults = null;
    }
}

