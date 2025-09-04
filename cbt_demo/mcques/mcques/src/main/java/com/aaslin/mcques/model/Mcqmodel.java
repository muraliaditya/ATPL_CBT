package com.aaslin.mcques.model;

import jakarta.persistence.*;

@Entity
@Table(name = "mcqs")
public class Mcqmodel {

    @Id
    @Column(name = "mcq_id")
    private String mcqId;

    @Column(name = "contest_id")
    private String contestId;

    @Column(columnDefinition = "TEXT")
    private String question;

    @Column(name = "correct_answer", columnDefinition = "TEXT")
    private String correctAnswer;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private QuestionType type; 

    @Column(name = "option_a")
    private String optionA;

    @Column(name = "option_b")
    private String optionB;

    @Column(name = "option_c")
    private String optionC;

    @Column(name = "option_d")
    private String optionD;

    public enum QuestionType {
        MULTIPLE,
        SINGLE
    }

    public String getMcqId() { 
    	return mcqId;
    	}
    public void setMcqId(String mcqId){
    	this.mcqId = mcqId;
    	}

    public String getContestId() {
    	return contestId;
    	}
    public void setContestId(String contestId) { 
    	this.contestId = contestId; 
    	}

    public String getQuestion() {
    	return question;
    	}
    public void setQuestion(String question) {
    	this.question = question;
    	}

    public String getCorrectAnswer() { 
    	return correctAnswer;
    	}
    public void setCorrectAnswer(String correctAnswer) { 
    	this.correctAnswer = correctAnswer;
    	}

    public QuestionType getType() { 
    	return type; 
    	}
    public void setType(QuestionType type) { 
    	this.type = type; 
    	}

    public String getOptionA() { 
    	return optionA;
    	}
    public void setOptionA(String optionA) {
    	this.optionA = optionA; 
    	}

    public String getOptionB() {
    	return optionB; 
    	}
    public void setOptionB(String optionB) { 
    	this.optionB = optionB;
    	}

    public String getOptionC() { 
    	return optionC; 
    	}
    public void setOptionC(String optionC) { 
    	this.optionC = optionC;
    	}

    public String getOptionD() {
    	return optionD;
    	}
    public void setOptionD(String optionD) { 
    	this.optionD = optionD;
    	}
}