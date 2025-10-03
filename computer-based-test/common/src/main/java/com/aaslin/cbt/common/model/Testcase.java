package com.aaslin.cbt.common.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "testcase_cbt")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Testcase {

    @Id
    @Column(name = "testcase_id", length = 50)
    private String testcaseId;

    @ManyToOne
    @JoinColumn(name = "coding_question_id")

    @JsonBackReference
    private CodingQuestion codingQuestion;
  
    @Column(name = "input_values", columnDefinition = "JSON")
    private String inputValues; 

    @Column(name = "expected_output", columnDefinition = "JSON")
    private String expectedOutput; 

    @Enumerated(EnumType.STRING)
    @Column(name = "testcase_type", length = 20)
    private TestcaseType testcaseType;

    @Column(name = "weightage",nullable = false)
    private Integer weightage;
    
    @Column(name = "description",columnDefinition = "TEXT")
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
    private List<DeveloperTestcaseResult> developerTestcaseResults;
    
    public enum TestcaseType {
    	PUBLIC, PRIVATE 
    }
}

