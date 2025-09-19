package com.aaslin.cbt.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "mcq_answers")
@Getter
@Setter
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
