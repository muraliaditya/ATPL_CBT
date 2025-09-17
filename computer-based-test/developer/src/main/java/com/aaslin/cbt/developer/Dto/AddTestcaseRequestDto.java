package com.aaslin.cbt.developer.Dto;

import lombok.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor

public class AddTestcaseRequestDto {
	private List<Object> inputValues;
    private Object output;
    private String description;
    private String testcaseType; 
    private Integer weightage;
}
