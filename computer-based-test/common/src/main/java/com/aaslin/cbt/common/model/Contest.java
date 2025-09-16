package com.aaslin.cbt.common.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "contests_cbt")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor

public class Contest {
    @Id
    @Column(name = "contest_id", length = 50)
    private String contestId;

    @Column(name = "contest_name")
    private String contestName;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "total_coding_questions")
    private Integer totalCodingQuestions;

    @Column(name = "total_quants_mcqs")
    private Integer totalQuantsMcqs;

    @Column(name = "total_reasoning_mcqs")
    private Integer totalReasoningMcqs;

    @Column(name = "total_verbal_mcqs")
    private Integer totalVerbalMcqs;

    @Column(name = "total_technical_mcqs")
    private Integer totalTechnicalMcqs;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ContestStatus status;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "contest")
    private List<ParticipantStatus> participantStatuses;

    @OneToMany(mappedBy = "contest")
    private List<Submission> submissions;

  public enum ContestStatus {
    ACTIVE, INACTIVE, COMPLETED
  }
  
}


