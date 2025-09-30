package com.aaslin.cbt.developer.Dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor

public class AddTestcaseRequestDto {
	private List<Object> inputValues;    
    private String expectedOutput;
    private String description;
    private String testcaseType; 
    private Integer weightage;
}