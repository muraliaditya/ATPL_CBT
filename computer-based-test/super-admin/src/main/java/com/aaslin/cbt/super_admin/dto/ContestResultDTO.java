package com.aaslin.cbt.super_admin.dto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor 
@AllArgsConstructor @Builder
public class ContestResultDTO {
    private String submissionId;
    private String participantId;
    private String username;
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
