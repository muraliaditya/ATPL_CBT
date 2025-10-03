package com.aaslin.cbt.common.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "coding_questions_cbt")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CodingQuestions {

    @Id
    @Column(name = "coding_question_id", length = 50)
    private String codingQuestionId;

    
    @Column(name = "question",nullable=false,length=1000)
    private String question;

   
    @Column(name = "description", columnDefinition = "TEXT", nullable=false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty", length = 20)
    private Difficulty difficulty;

    
    @Column(name = "output_format", columnDefinition = "TEXT",nullable=false)
    private String outputFormat;

    @Enumerated(EnumType.STRING)
    @Column(name = "approval_status", length = 20)
    private ApprovalStatus approvalStatus;

    @ManyToOne
    @JoinColumn(name = "created_by")
    @JsonIgnore
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "approved_by")
    @JsonIgnore
    private User approvedBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name="method_name",nullable=false)
    private String methodName;

    
    @Column(name = "java_boiler_code", columnDefinition = "TEXT",nullable=false)
    private String javaBoilerCode;

   
    @Column(name = "python_boiler_code", columnDefinition = "TEXT",nullable=false)
    private String pythonBoilerCode;

    @Column(name = "execution_time_limit", precision = 3,scale=2)
    private BigDecimal executionTimeLimit;

    @Column(name = "memory_limit")
    private Long memoryLimit;

    
    @Column(name = "input_type", columnDefinition = "JSON",nullable=false)
    private String inputType; 

    
    @Column(name = "input_params", columnDefinition = "JSON",nullable=false)
    private String inputParams; 
    
    @Column(name="is_active")
    private Boolean isActive = true;
    
    @Column(name = "weightage",nullable = false)
    private Integer weightage;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    @JsonIgnore
    private User updatedBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "codingQuestion",cascade=CascadeType.ALL,orphanRemoval=true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Testcases> testcases;

    public enum Difficulty { 
    	EASY, MEDIUM, HARD 
    }
    
    public enum ApprovalStatus { 
    	PENDING, APPROVED, REJECTED
    }

}