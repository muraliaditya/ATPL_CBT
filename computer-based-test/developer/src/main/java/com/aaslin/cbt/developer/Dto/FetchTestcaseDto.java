package com.aaslin.cbt.developer.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class FetchTestcaseDto {
	
	private String testcaseId;
    private Object inputValues;
    private Object expectedOutput;
    private String description;
    private int weightage;
}
