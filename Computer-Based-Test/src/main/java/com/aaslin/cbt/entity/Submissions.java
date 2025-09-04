package com.aaslin.cbt.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "submissions")
@Getter
@Setter
public class Submissions {
	
	@Id
	@Column(name = "submission_id", length = 100)
	private String submissionId;
	
	@Column(name = "contest_id", length = 100)
	private String contestId;
	
	@Column(name = "user_id", length = 100)
	private String userId;
	
	@Column(name = "mcq_submission_id", length = 100)
	private String mcqSubmissionId;
	
	@Column(name = "coding_submission_id", length = 100)
	private String codingSubmissionId;
	
	@Column(name = "total_coding_score")
	private int totalCodingScore;
	
	@Column(name = "total_mcq_score")
	private int totalMcqScore;
	
	// Relationships
	@ManyToOne
	@JoinColumn(name = "contest_id", insertable = false, updatable = false)
	private Contest contest;
	
	@ManyToOne
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	private User user;
	
	@OneToMany(mappedBy = "submission", cascade = jakarta.persistence.CascadeType.ALL)
	private List<MCQAnswers> mcqAnswers;
	
	@OneToMany(mappedBy = "submission", cascade = jakarta.persistence.CascadeType.ALL)
	private List<CodingSubmissionDetails> codingSubmissionDetails;
	
	public Contest getContest() {
		return contest;
	}
	
	public void setContest(Contest contest) {
		this.contest = contest;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public List<MCQAnswers> getMcqAnswers() {
		return mcqAnswers;
	}
	
	public void setMcqAnswers(List<MCQAnswers> mcqAnswers) {
		this.mcqAnswers = mcqAnswers;
	}
	
	public List<CodingSubmissionDetails> getCodingSubmissionDetails() {
		return codingSubmissionDetails;
	}
	
	public void setCodingSubmissionDetails(List<CodingSubmissionDetails> codingSubmissionDetails) {
		this.codingSubmissionDetails = codingSubmissionDetails;
	}
}
