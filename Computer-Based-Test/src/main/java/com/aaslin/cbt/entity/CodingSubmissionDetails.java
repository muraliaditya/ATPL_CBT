package com.aaslin.cbt.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CodingSubmissionDetails {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String codingSubmissionId;
	public String getCodingSubmissionId() {
		return codingSubmissionId;
	}

	public void setCodingSubmissionId(String codingSubmissionId) {
		this.codingSubmissionId = codingSubmissionId;
	}

	public String getCodingQuestionId() {
		return codingQuestionId;
	}

	public void setCodingQuestionId(String codingQuestionId) {
		this.codingQuestionId = codingQuestionId;
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

	public boolean isIs_final() {
		return is_final;
	}

	public void setIs_final(boolean is_final) {
		this.is_final = is_final;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getSubmitted_at() {
		return submitted_at;
	}

	public void setSubmitted_at(LocalDateTime submitted_at) {
		this.submitted_at = submitted_at;
	}

	private String codingQuestionId;
	@Enumerated(EnumType.STRING)
	@Column(name = "language")
	private Language language;
	private int score;
	private String testcasesPassed;
	private String code;
	private boolean is_final;
	private String status;
	private LocalDateTime submitted_at;
	
	public enum Language{
		C,
		Cpp,
		Java,
		Python
	}
	
	

}
