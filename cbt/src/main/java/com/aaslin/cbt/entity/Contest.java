package com.aaslin.cbt.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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

import lombok.Data;

@Entity
@Table(name = "contests")
@Data
public class Contest {

    @Id
    @Column(name = "contest_id", length = 100)
    private String contestId; 

    @Column(name = "contest_name", length = 255)
    private String contestName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "allowed_college_id", columnDefinition = "TEXT")
    private String allowedCollegeId;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "duration")
    private LocalTime duration;

    @Column(name = "total_mcq_questions")
    private int totalMcqQuestions;

    @Column(name = "total_coding_questions")
    private int totalCodingQuestions;

    @Column(name = "created_by", length = 100)
    private String createdBy;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "created_by", insertable = false, updatable = false)
    @JsonBackReference
    private User creator;

    @OneToMany(mappedBy = "contest", cascade = CascadeType.ALL)
	@JsonIgnore
	@JsonManagedReference
    private List<MCQQuestion> mcqQuestions;

    @OneToMany(mappedBy = "contest", cascade = CascadeType.ALL)
    private List<CodingQuestion> codingQuestions;

    @OneToMany(mappedBy = "contest", cascade = CascadeType.ALL)
    private List<Submissions> submissions;

    public enum Status {
        ACTIVE,
        INACTIVE
    }
}
