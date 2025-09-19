package com.aaslin.cbt.super_admin.dto;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class McqSectionDTO {
    private String section;             
    private Integer sectionWeightage;   
    private List<McqQuestionDTO> mcqQuestions;
}
