package com.aaslin.cbt.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "mcq_answers")
public class MCQAnswers {
	
	@Id
	@Column(name = "mcq_answer_id", length = 100)
	private String mcqAnswerId;
	
	@Column(name = "mcq_submission_id", length = 100)
	private String mcqSubmissionId;
	
	@Column(name = "mcq_id", length = 100)
	private String mcqId;
	
	@Column(name = "selected_option", length = 255)
	private String selectedOption;
	
	@Column(name = "is_correct")
	private boolean isCorrect;
	
	// Relationships
	@ManyToOne
	@JoinColumn(name = "mcq_submission_id", insertable = false, updatable = false)
	private Submissions submission;
	
	@ManyToOne
	@JoinColumn(name = "mcq_id", insertable = false, updatable = false)
	private MCQQuestion mcq;
	
	// Getters and Setters
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
		return selectedOption;
	}
	
	public void setSelectedOption(String selectedOption) {
		this.selectedOption = selectedOption;
	}
	
	public boolean isCorrect() {
		return isCorrect;
	}
	
	public void setCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}
	
	public Submissions getSubmission() {
		return submission;
	}
	
	public void setSubmission(Submissions submission) {
		this.submission = submission;
	}
	
	public MCQQuestion getMcq() {
		return mcq;
	}
	
	public void setMcq(MCQQuestion mcq) {
		this.mcq = mcq;
	}
}
