package com.aaslin.cbt.super_admin.dto;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class CodingSubmissionDTO {
    private String codingSubmissionId;
    private String codingQuestionId;
    private String questionTitle;
    private String languageUsed;
    private String code;
    private Integer score;
    private Integer publicTestcasesPassed;
    private Integer privateTestcasesPassed;
    private Boolean isFinalAttempt;
    private LocalDateTime submittedAt;
    private String status;
}
