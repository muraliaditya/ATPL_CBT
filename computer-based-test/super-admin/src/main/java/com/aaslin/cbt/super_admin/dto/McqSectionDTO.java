package com.aaslin.cbt.super_admin.dto;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class McqSectionDTO {
    private String section;             
    private Integer sectionWeightage;   
    private List<McqQuestionDTO> mcqQuestions;
    private List<McqQuestionAnswerDTO> mcqQuestionAnswer;
  
}





