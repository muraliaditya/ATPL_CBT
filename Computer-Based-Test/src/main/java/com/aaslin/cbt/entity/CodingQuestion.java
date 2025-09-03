package com.aaslin.cbt.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CodingQuestion {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long CodingQuestionId;
	private String questiontext;
	private String inputType;
	private String outputtype;
	private String Descrpition;
	private String difficulty;
	
	public Long getCodingQuestionId() {
		return CodingQuestionId;
	}
	public void setCodingQuestionId(Long codingQuestionId) {
		CodingQuestionId = codingQuestionId;
	}
	public String getQuestiontext() {
		return questiontext;
	}
	public void setQuestiontext(String questiontext) {
		this.questiontext = questiontext;
	}
	public String getInputType() {
		return inputType;
	}
	public void setInputType(String inputType) {
		this.inputType = inputType;
	}
	public String getOutputtype() {
		return outputtype;
	}
	public void setOutputtype(String outputtype) {
		this.outputtype = outputtype;
	}
	public String getDescrpition() {
		return Descrpition;
	}
	public void setDescrpition(String descrpition) {
		Descrpition = descrpition;
	}
	public String getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	
	
	
}
