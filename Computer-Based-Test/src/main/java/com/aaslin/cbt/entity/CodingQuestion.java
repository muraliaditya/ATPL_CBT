package com.aaslin.cbt.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "coding_questions")
@Getter
@Setter
public class CodingQuestion {
	
	@Id
	@Column(name = "coding_question_id", length = 100)
	private String codingQuestionId;
	
	@Column(name = "question", columnDefinition = "TEXT")
	private String question;
	
	@Column(name = "contest_id", length = 100)
	private String contestId;
	
	@Column(name = "description", columnDefinition = "TEXT")
	private String description;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "difficulty")
	private Difficulty difficulty;
	
	@Column(name = "input_type", length = 100)
	private String inputType;
	
	@Column(name = "output_type", length = 100)
	private String outputType;
	
	// Relationships
	@ManyToOne
	@JoinColumn(name = "contest_id", insertable = false, updatable = false)
	private Contest contest;
	
	@OneToMany(mappedBy = "codingQuestion", cascade = jakarta.persistence.CascadeType.ALL)
	private List<TestCases> testCases;
	
	@OneToMany(mappedBy = "codingQuestion", cascade = jakarta.persistence.CascadeType.ALL)
	private List<CodingSubmissionDetails> codingSubmissionDetails;
	
	public Difficulty getDifficulty() {
		return difficulty;
	}
	
	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}
	
	public Contest getContest() {
		return contest;
	}
	
	public void setContest(Contest contest) {
		this.contest = contest;
	}
	
	public List<TestCases> getTestCases() {
		return testCases;
	}
	
	public void setTestCases(List<TestCases> testCases) {
		this.testCases = testCases;
	}
	
	public List<CodingSubmissionDetails> getCodingSubmissionDetails() {
		return codingSubmissionDetails;
	}
	
	public void setCodingSubmissionDetails(List<CodingSubmissionDetails> codingSubmissionDetails) {
		this.codingSubmissionDetails = codingSubmissionDetails;
	}
	
	public enum Difficulty {
		EASY,
		MEDIUM,
		HIGH
	}
}
