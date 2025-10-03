package com.aaslin.cbt.super_admin.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionRequestDTO {
    private String questionId;
    private String questionText;
    private String createdBy;
    private String type; 
    private String approvalStatus;
    private String userId;
}
