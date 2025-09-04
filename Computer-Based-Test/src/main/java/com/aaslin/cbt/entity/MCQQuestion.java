package com.aaslin.cbt.entity;

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
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "mcqs")
@Getter
@Setter
public class MCQQuestion {
	
	@Id
	@Column(name = "mcq_id", length = 100)
	private String mcqId;
	
	@Column(name = "question", columnDefinition = "TEXT")
	private String question;
	
	@Column(name = "correct_answer", columnDefinition = "TEXT")
	private String correctAnswer;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "type")
	private Type type = Type.SINGLE;
	
	@Column(name = "option_A", length = 255)
	private String optionA;
	
	@Column(name = "option_B", length = 255)
	private String optionB;
	
	@Column(name = "option_C", length = 255)
	private String optionC;
	
	@Column(name = "option_D", length = 255)
	private String optionD;
	
	@Column(name = "contest_id", length = 100)
	private String contestId;
	
	// Relationships
	@ManyToOne
	@JoinColumn(name = "contest_id", insertable = false, updatable = false)
	private Contest contest;
	
	@OneToMany(mappedBy = "mcq", cascade = CascadeType.ALL)
	private List<MCQAnswers> mcqAnswers;
	
	public Type getType() {
		return type;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	
	public Contest getContest() {
		return contest;
	}
	
	public void setContest(Contest contest) {
		this.contest = contest;
	}
	
	public List<MCQAnswers> getMcqAnswers() {
		return mcqAnswers;
	}
	
	public void setMcqAnswers(List<MCQAnswers> mcqAnswers) {
		this.mcqAnswers = mcqAnswers;
	}
	
	public enum Type {
		SINGLE,
		MULTIPLE
	}
}
