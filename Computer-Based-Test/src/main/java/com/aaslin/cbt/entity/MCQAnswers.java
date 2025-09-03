package com.aaslin.cbt.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity

public class MCQAnswers {
	
	@Id
	private String mcqAnswerId;
	private String mcqSubmissionId;
	private String mcqId;
	private String SelectedOption;
	private boolean isCorrect;
	
	public String getMcqAnswerId() {
		return mcqAnswerId;
	}
	public void setMcqAnswerId(String mcqAnswerId) {
		this.mcqAnswerId = mcqAnswerId;
	}
	public String getMcqSubmissionId() {
		return mcqSubmissionId;
	}
	public void setMcqSubmissionId(String mcqSubmissionId) {
		this.mcqSubmissionId = mcqSubmissionId;
	}
	public String getMcqId() {
		return mcqId;
	}
	public void setMcqId(String mcqId) {
		this.mcqId = mcqId;
	}
	public String getSelectedOption() {
		return SelectedOption;
	}
	public void setSelectedOption(String selectedOption) {
		SelectedOption = selectedOption;
	}
	public boolean isCorrect() {
		return isCorrect;
	}
	public void setCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}
	

}
