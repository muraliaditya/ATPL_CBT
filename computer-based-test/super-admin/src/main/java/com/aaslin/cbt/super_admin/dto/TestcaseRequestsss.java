package com.aaslin.cbt.super_admin.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class TestcaseRequestsss {
	
	private String testcasesId;
	private List<Object> inputs;
	private Object output;
	private Integer weightage;
	private String testcaseType;

}
