package com.aaslin.cbt.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.CascadeType;
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
@Table(name = "contests")
public class Contest {
	
	@Id
	@Column(name = "contest_id", length = 100)
	private String contestId;
	
	@Column(name = "contest_name", length = 255)
	private String contestName;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private Status status;
	
	@Column(name = "allowed_college_id", columnDefinition = "TEXT")
	private String allowedCollegeId;
	
	@Column(name = "start_time")
	private LocalDateTime startTime;
	
	@Column(name = "end_time")
	private LocalDateTime endTime;
	
	@Column(name = "duration")
	private LocalTime duration;
	
	@Column(name = "total_mcq_questions")
	private int totalMcqQuestions;
	
	@Column(name = "total_coding_questions")
	private int totalCodingQuestions;
	
	@Column(name = "created_by", length = 100)
	private String createdBy;
	
	// Relationships
	@ManyToOne
	@JoinColumn(name = "created_by", insertable = false, updatable = false)
	private User creator;
	
	@OneToMany(mappedBy = "contest", cascade = CascadeType.ALL)
	private List<MCQQuestion> mcqQuestions;
	
	@OneToMany(mappedBy = "contest", cascade = CascadeType.ALL)
	private List<CodingQuestion> codingQuestions;
	
	@OneToMany(mappedBy = "contest", cascade = CascadeType.ALL)
	private List<Submissions> submissions;
	
	// Getters and Setters
	public String getContestId() {
		return contestId;
	}
	
	public void setContestId(String contestId) {
		this.contestId = contestId;
	}
	
	public String getContestName() {
		return contestName;
	}
	
	public void setContestName(String contestName) {
		this.contestName = contestName;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public String getAllowedCollegeId() {
		return allowedCollegeId;
	}
	
	public void setAllowedCollegeId(String allowedCollegeId) {
		this.allowedCollegeId = allowedCollegeId;
	}
	
	public LocalDateTime getStartTime() {
		return startTime;
	}
	
	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}
	
	
	public LocalDateTime getEndTime() {
		return endTime;
	}
	
	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}
	
	public LocalTime getDuration() {
		return duration;
	}
	
	public void setDuration(LocalTime duration) {
		this.duration = duration;
	}
	
	public int getTotalMcqQuestions() {
		return totalMcqQuestions;
	}
	
	public void setTotalMcqQuestions(int totalMcqQuestions) {
		this.totalMcqQuestions = totalMcqQuestions;
	}
	
	public int getTotalCodingQuestions() {
		return totalCodingQuestions;
	}
	
	public void setTotalCodingQuestions(int totalCodingQuestions) {
		this.totalCodingQuestions = totalCodingQuestions;
	}
	
	public String getCreatedBy() {
		return createdBy;
	}
	
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	public User getCreator() {
		return creator;
	}
	
	public void setCreator(User creator) {
		this.creator = creator;
	}
	
	public List<MCQQuestion> getMcqQuestions() {
		return mcqQuestions;
	}
	
	public void setMcqQuestions(List<MCQQuestion> mcqQuestions) {
		this.mcqQuestions = mcqQuestions;
	}
	
	public List<CodingQuestion> getCodingQuestions() {
		return codingQuestions;
	}
	
	public void setCodingQuestions(List<CodingQuestion> codingQuestions) {
		this.codingQuestions = codingQuestions;
	}
	
	public List<Submissions> getSubmissions() {
		return submissions;
	}
	
	public void setSubmissions(List<Submissions> submissions) {
		this.submissions = submissions;
	}
	
	public enum Status {
		ACTIVE,
		INACTIVE
	}
}

