package com.aaslin.cbt.developer.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor

public class AddTestcaseRequestDto {
	private String inputValues;    // Frontend sends: "["[2,7,11,15]","9"]"
    private String expectedOutput;
    private String description;
    private String testcaseType; 
    private Integer weightage;
}
