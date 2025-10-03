package com.aaslin.cbt.common.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "submissions_cbt")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Submission {
    @Id
    @Column(name = "submission_id", length = 50)
    private String submissionId;

    @ManyToOne
    @JoinColumn(name = "contest_id")
    private Contest contest;

    @ManyToOne
    @JoinColumn(name = "participant_id")
    @JsonBackReference
    private Participant participant;

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;

    @Column(name = "total_coding_score")
    private Integer totalCodingScore;

    @Column(name = "total_mcq_score")
    private Integer totalMcqScore;

    @Column(name = "total_score")
    private Integer totalScore;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "submission")
    private List<CodingSubmission> codingSubmissions;

    @OneToMany(mappedBy = "submission")
    private List<McqAnswer> mcqAnswers;
}


