package com.cbt.mcq_test.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "mcq_questions")
@Getter
@Setter
public class McqQuestion {

    @Id
    @Column(name = "mcq_id", length = 36)
    private String mcqId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String questionText;

    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String correctAnswer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Section section;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createBy;
    private String updatedBy;

    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus;

    private String approvedBy;
    private Integer weightage;

    @OneToMany(mappedBy = "mcqQuestion", cascade = CascadeType.ALL)
    private List<ContestMcqMap> contestMappings;

    public enum Section {
        QUANTS, VERBAL, TECHNICAL, REASONING
    }

    public enum ApprovalStatus {
        PENDING, APPROVED, REJECTED
    }
}
