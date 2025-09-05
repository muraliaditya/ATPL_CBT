package com.aaslin.code_runner.entity;

import java.time.LocalDateTime;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="coding_submission_details")
@Data
public class CodingSubmission {

	@Id
	@Column(name="coding_submission_id")
	private String codingSubmissionId;
	
	
	@Column(name="coding_question_id")
	private String codingQuestionId;
	
	@Enumerated(EnumType.STRING)
	private Language language;
	
	private int score;
	
	@Column(name="testcases_passed",columnDefinition="TEXT")
	private String testcasesPassed;
	
	@Column(columnDefinition="TEXT")
	private String code;
	
	@Column(name="is_final")
	private boolean isFinal;
	
	private String status;
	
	@Column(name="submitted_at")
	private LocalDateTime submittedAt;
	

	public enum Language {
	PYTHON,CPP,JAVA,NODE,C
		
	}

	
}
