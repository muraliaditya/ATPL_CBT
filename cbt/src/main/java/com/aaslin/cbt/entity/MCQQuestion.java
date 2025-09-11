package com.aaslin.cbt.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
@Table(name = "mcq_questions")
public class MCQQuestion {

    @Id
    @Column(name = "mcq_id", length = 100)
    private String mcqId;   

    @Column(nullable = false)
    private String question;

    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;

    private String correctanswer;

    private int marks;

    @ManyToOne
    @JoinColumn(name = "contest_id", nullable = false)
    @JsonBackReference
    private Contest contest;

    // Getters & Setters
    public String getMcqId() {
        return mcqId;
    }
    public void setMcqId(String mcqId) {
        this.mcqId = mcqId;
    }
    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
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
    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }
    public String getCorrectanswer() {
        return correctanswer;
    }
    public void setCorrectanswer(String correctanswer) {
        this.correctanswer = correctanswer;
    }
    public int getMarks() {
        return marks;
    }
    public void setMarks(int marks) {
        this.marks = marks;
    }
    public Contest getContest() {
        return contest;
    }
    public void setContest(Contest contest) {
        this.contest = contest;
    }

    public enum Type {
        SINGLE,
        MULTIPLE
    }
}
