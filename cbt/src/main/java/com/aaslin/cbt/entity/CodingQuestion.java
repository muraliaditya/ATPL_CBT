package com.aaslin.cbt.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "coding_questions")
@Getter
@Setter
public class CodingQuestion {

    @Id
    @Column(name = "coding_question_id", length = 100)
    private String codingQuestionId;  // will be generated like CODQ01, CODQ02

    @Column(name = "question", columnDefinition = "TEXT")
    private String question;

    @Column(name = "contest_id", length = 100)
    private String contestId;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty")
    private Difficulty difficulty;

    @Column(name = "input_type", length = 100)
    private String inputType;

    @Column(name = "output_type", length = 100)
    private String outputType;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "contest_id", insertable = false, updatable = false)
    private Contest contest;

    @OneToMany(mappedBy = "codingQuestion", cascade = CascadeType.ALL)
    private List<TestCases> testCases;

    @OneToMany(mappedBy = "codingQuestion", cascade = CascadeType.ALL)
    private List<CodingSubmissionDetails> codingSubmissionDetails;

    public enum Difficulty {
        EASY,
        MEDIUM,
        HIGH
    }
}
