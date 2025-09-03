package com.aaslin.cbt.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;



@Entity
public class Contest {
	
	@Id
	private String contestId;
	private String contestname;
	
	@Enumerated(EnumType.STRING)
	@Column(name="status")
	private Status status;
	private String allowedCollegeId;
	private LocalDateTime startTime;
	private String Instructions;
	private LocalDateTime endTime;
	private LocalDateTime duration;
	private int totalMcqQuestions;
	private int totalCodingQuestions;
	private String createdBy;
    public String getContestId() {
		return contestId;
	}
	public void setContestId(String contestId) {
		this.contestId = contestId;
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
	public String getInstructions() {
		return Instructions;
	}
	public void setInstructions(String instructions) {
		Instructions = instructions;
	}
	public LocalDateTime getEndTime() {
		return endTime;
	}
	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}
	public LocalDateTime getDuration() {
		return duration;
	}
	public void setDuration(LocalDateTime duration) {
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
	
	
	@OneToMany(mappedBy = "contest", cascade = CascadeType.ALL)
    private List<MCQQuestion> mcqQuestions;
	
	public List<MCQQuestion> getMcqQuestions() {
		return mcqQuestions;
	}
	public void setMcqQuestions(List<MCQQuestion> mcqQuestions) {
		this.mcqQuestions = mcqQuestions;
	}
	public String getContestid() {
		return contestId;
	}
	public void setContestid(String contestid) {
		this.contestId = contestid;
	}
	public String getContestname() {
		return contestname;
	}
	public void setContestname(String contestname) {
		this.contestname = contestname;
	}
	public enum Status{
		Active,
		InActive,
		Completed
	}
	
}

