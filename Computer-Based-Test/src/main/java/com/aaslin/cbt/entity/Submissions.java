package com.aaslin.cbt.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Submissions {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long SubmissionId;
	public Long getSubmissionId() {
		return SubmissionId;
	}
	public void setSubmissionId(Long submissionId) {
		SubmissionId = submissionId;
	}
	public int getTotalCodingScore() {
		return totalCodingScore;
	}
	public void setTotalCodingScore(int totalCodingScore) {
		this.totalCodingScore = totalCodingScore;
	}
	public int getTotalMcqScore() {
		return totalMcqScore;
	}
	public void setTotalMcqScore(int totalMcqScore) {
		this.totalMcqScore = totalMcqScore;
	}
	private int totalCodingScore;
	private int totalMcqScore;
	@OneToOne
	@JoinColumn(name = "UserId")
	private User user;
	
	

}
