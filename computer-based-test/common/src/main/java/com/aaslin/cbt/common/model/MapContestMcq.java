package com.aaslin.cbt.common.model;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "map_contest_mcq_cbt")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class MapContestMcq {
    @Id
    @Column(name = "map_contest_mcq_id", length = 50)
    private String contestMcqMapId;

    @ManyToOne
    @JoinColumn(name = "contest_id")
    private Contest contest;

    @ManyToOne
    @JoinColumn(name = "mcq_question_id")
    private McqQuestion mcqQuestion;

    @Column(name = "assigned_at")
    private LocalDateTime assignedAt;

    @Column(name = "weightage")
    private Integer weightage;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
