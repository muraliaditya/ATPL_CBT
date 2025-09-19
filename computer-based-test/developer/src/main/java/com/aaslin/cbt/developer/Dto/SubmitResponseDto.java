package com.aaslin.cbt.developer.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class SubmitResponseDto {
    private String codeStatus;
    private String message;
    private int publicTestcasesPassed;
    private int privateTestcasesPassed;
    private List<PublicTestcaseResultDto> publicTestcaseResults;
    private List<PrivateTestcaseResultDto> privateTestcaseResults;

    public SubmitResponseDto(String codeStatus, String message) {
        this.codeStatus = codeStatus;
        this.message = message;
        this.publicTestcasesPassed = 0;
        this.privateTestcasesPassed = 0;
        this.publicTestcaseResults = null;
        this.privateTestcaseResults = null;
    }
}
