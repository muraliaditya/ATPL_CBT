package com.aaslin.cbt.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "coding_submission_details")
@IdClass(CodingSubmissionDetailsId.class)
public class CodingSubmissionDetails {
	
	@Id
	@Column(name = "coding_question_id", length = 100)
	private String codingQuestionId;
	
	@Id
	@Column(name = "coding_submission_id", length = 100)
	private String codingSubmissionId;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "language")
	private Language language;
	
	@Column(name = "score")
	private int score;
	
	@Column(name = "testcases_passed", columnDefinition = "TEXT")
	private String testcasesPassed;
	
	@Column(name = "code", columnDefinition = "TEXT")
	private String code;
	
	@Column(name = "is_final")
	private boolean isFinal;
	
	@Column(name = "status", length = 50)
	private String status;
	
	@Column(name = "submitted_at")
	private LocalDateTime submittedAt;
	
	// Relationships
	@ManyToOne
	@JoinColumn(name = "coding_question_id", insertable = false, updatable = false)
	private CodingQuestion codingQuestion;
	
	@ManyToOne
	@JoinColumn(name = "coding_submission_id", insertable = false, updatable = false)
	private Submissions submission;
	
	// Getters and Setters
	public String getCodingQuestionId() {
		return codingQuestionId;
	}
	
	public void setCodingQuestionId(String codingQuestionId) {
		this.codingQuestionId = codingQuestionId;
	}
	
	public String getCodingSubmissionId() {
		return codingSubmissionId;
	}
	
	public void setCodingSubmissionId(String codingSubmissionId) {
		this.codingSubmissionId = codingSubmissionId;
	}
	
	public Language getLanguage() {
		return language;
	}
	
	public void setLanguage(Language language) {
		this.language = language;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public String getTestcasesPassed() {
		return testcasesPassed;
	}
	
	public void setTestcasesPassed(String testcasesPassed) {
		this.testcasesPassed = testcasesPassed;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public boolean isFinal() {
		return isFinal;
	}
	
	public void setFinal(boolean isFinal) {
		this.isFinal = isFinal;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public LocalDateTime getSubmittedAt() {
		return submittedAt;
	}
	
	public void setSubmittedAt(LocalDateTime submittedAt) {
		this.submittedAt = submittedAt;
	}
	
	public CodingQuestion getCodingQuestion() {
		return codingQuestion;
	}
	
	public void setCodingQuestion(CodingQuestion codingQuestion) {
		this.codingQuestion = codingQuestion;
	}
	
	public Submissions getSubmission() {
		return submission;
	}
	
	public void setSubmission(Submissions submission) {
		this.submission = submission;
	}
	
	public enum Language {
		CPP,
		JAVA,
		PYTHON
	}
}
