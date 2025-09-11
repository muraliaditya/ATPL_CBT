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

@Entity
@Table(name = "coding_questions")
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
	
	// Getters and Setters
	public String getCodingQuestionId() {
		return codingQuestionId;
	}
	
	public void setCodingQuestionId(String codingQuestionId) {
		this.codingQuestionId = codingQuestionId;
	}
	
	public String getQuestion() {
		return question;
	}
	
	public void setQuestion(String question) {
		this.question = question;
	}
	
	public String getContestId() {
		return contestId;
	}
	
	public void setContestId(String contestId) {
		this.contestId = contestId;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Difficulty getDifficulty() {
		return difficulty;
	}
	
	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}
	
	public String getInputType() {
		return inputType;
	}
	
	public void setInputType(String inputType) {
		this.inputType = inputType;
	}
	
	public String getOutputType() {
		return outputType;
	}
	
	public void setOutputType(String outputType) {
		this.outputType = outputType;
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
