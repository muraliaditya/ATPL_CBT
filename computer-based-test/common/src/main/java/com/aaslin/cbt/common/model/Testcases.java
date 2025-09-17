package com.aaslin.cbt.common.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "testcases_cbt")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Testcases {

    @Id
    @Column(name = "testcase_id", length = 50)
    private String testcaseId;

    @ManyToOne
    @JoinColumn(name = "coding_question_id")
    private CodingQuestions codingQuestion;
  
    @Column(name = "input_values", columnDefinition = "JSON")
    private String inputValues; 

    @Column(name = "expected_output", columnDefinition = "JSON")
    private String expectedOutput; 

    @Enumerated(EnumType.STRING)
    @Column(name = "testcase_type", length = 20)
    private TestcaseType testcaseType;

    @Column(name = "weightage",nullable = false)
    private Integer weightage;
    
    @Column(name = "description",nullable=false,columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;
    
    @OneToMany(mappedBy = "testcase")
    private List<TestcaseResult> testcaseResults;

    @OneToMany(mappedBy = "testcase")
    private List<DeveloperTestcaseResults> developerTestcaseResults;
    
    public enum TestcaseType {
    	PUBLIC, PRIVATE 
    }
}

