package com.aaslin.cbt.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
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
@Table(name = "contests")
@Getter
@Setter
public class Contest {
	
	@Id
	@Column(name = "contest_id", length = 100)
	private String contestId;
	
	@Column(name = "contest_name", length = 255)
	private String contestName;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private Status status;
	
	@ElementCollection
	@CollectionTable(name = "contest_allowed_colleges", joinColumns = @JoinColumn(name = "contest_id"))
	@Column(name = "college_uid")
	private List<String> allowedCollegeUids;
	
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

