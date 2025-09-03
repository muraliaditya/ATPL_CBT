package com.aaslin.cbtdemo.cbt_demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name="mcq_questions")
public class Mcq_questions {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int mcq_id;
	String question;
	String answer;
	String option1;
	String option2;
	String option3;
	String option4;
	public int getMcq_id() {
		return mcq_id;
	}
	public void setMcq_id(int mcq_id) {
		this.mcq_id = mcq_id;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getOption1() {
		return option1;
	}
	public void setOption1(String option1) {
		this.option1 = option1;
	}
	public String getOption2() {
		return option2;
	}
	public void setOption2(String option2) {
		this.option2 = option2;
	}
	public String getOption3() {
		return option3;
	}
	public void setOption3(String option3) {
		this.option3 = option3;
	}
	public String getOption4() {
		return option4;
	}
	public void setOption4(String option4) {
		this.option4 = option4;
	}
	
	
}
