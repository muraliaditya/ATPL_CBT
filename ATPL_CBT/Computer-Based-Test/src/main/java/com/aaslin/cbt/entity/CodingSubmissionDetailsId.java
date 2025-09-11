package com.aaslin.cbt.entity;

import java.io.Serializable;
import java.util.Objects;

public class CodingSubmissionDetailsId implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String codingQuestionId;
	private String codingSubmissionId;
	
	public CodingSubmissionDetailsId() {}
	
	public CodingSubmissionDetailsId(String codingQuestionId, String codingSubmissionId) {
		this.codingQuestionId = codingQuestionId;
		this.codingSubmissionId = codingSubmissionId;
	}
	
	public String getCodingQuestionId() {
		return codingQuestionId;
	}
	
	public void setCodingQuestionId(String codingQuestionId) {
		this.codingQuestionId = codingQuestionId;
	}
	
	public String getCodingSubmissionId() {
		return codingSubmissionId;
	}
	
	public void setCodingSubmissionId(String codingSubmissionId) {
		this.codingSubmissionId = codingSubmissionId;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		CodingSubmissionDetailsId that = (CodingSubmissionDetailsId) obj;
		return codingQuestionId.equals(that.codingQuestionId) && 
			   codingSubmissionId.equals(that.codingSubmissionId);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(codingQuestionId,codingSubmissionId);
	}
}
