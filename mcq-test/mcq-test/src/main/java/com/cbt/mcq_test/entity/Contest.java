package com.cbt.mcq_test.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "contests")
@Getter
@Setter
@Data
public class Contest {

    @Id
    @Column(name = "contest_id", length = 36)
    private String contestId;

    @Column(nullable = false, length = 255)
    private String contestName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Category category;

    private Integer totalQuantsMcqs;
    private Integer totalVerbalMcqs;
    private Integer totalTechnicalMcqs;
    private Integer totalReasoningMcqs;
    private Integer codingQuestions;
    private Integer duration; 
    private LocalDateTime startTime;

    @Enumerated(EnumType.STRING)
    private ContestStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;

    @OneToMany(mappedBy = "contest", cascade = CascadeType.ALL)
    @JsonManagedReference 
    private List<ContestMcqMap> contestMappings;

    public enum Category {
        STUDENT, PRELIMINARY, ADVANCED
    }

    public enum ContestStatus {
        DRAFT, ACTIVE, COMPLETED
    }
}
