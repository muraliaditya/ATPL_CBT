package com.aaslin.cbt.super_admin.dto;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonInclude;
@Getter
@Setter
@NoArgsConstructor 
@AllArgsConstructor @Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContestResultDTO {
    private String submissionId;
    private String participantId;
    private String userName;
    private String email;

    private String college;
    private String collegeRegdNo;
    private Double percentage;

    private String company;
    private String designation;
    private String overallExperience;

    private Integer codingMarks;
    private Integer mcqMarks;
    private Integer totalMarks;
}
