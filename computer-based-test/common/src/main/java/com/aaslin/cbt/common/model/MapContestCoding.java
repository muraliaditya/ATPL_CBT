package com.aaslin.cbt.common.model;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "map_contest_coding_cbt")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class MapContestCoding {
    @Id
    @Column(name = "map_contest_coding_id", length = 50)
    private String contestCodingMapId;

    @ManyToOne
    @JoinColumn(name = "contest_id")
    private Contest contest;

    @ManyToOne
    @JoinColumn(name = "coding_question_id")
    private CodingQuestion codingQuestion;

    @Column(name = "assigned_at")
    private LocalDateTime assignedAt;

    @Column(name = "execution_time_limit", precision = 10, scale = 2)
    private BigDecimal executionTimeLimit;

    @Column(name = "memory_limit")
    private Long memoryLimit;
    
    @Column(name="weightage")
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