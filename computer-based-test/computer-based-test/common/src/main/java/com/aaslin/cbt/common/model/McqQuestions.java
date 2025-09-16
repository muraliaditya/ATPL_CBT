package com.aaslin.cbt.common.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "mcq_questions_cbt")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class McqQuestions {

    @Id
    @Column(name = "mcq_question_id", length = 50)
    private String mcqQuestionId;

    @Column(name = "question_text", columnDefinition = "TEXT")
    private String questionText;

    @Column(name = "option_1", length = 500)
    private String option1;

    @Column(name = "option_2", length = 500)
    private String option2;

    @Column(name = "option_3", length = 500)
    private String option3;

    @Column(name = "option_4", length = 500)
    private String option4;

    @Column(name = "answer_key", length = 50)
    private String answerKey;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Sections section;

    @Enumerated(EnumType.STRING)
    @Column(name = "approval_status", length = 20)
    private ApprovalStatus approvalStatus;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "approved_by")
    private User approvedBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "weightage")
    private Integer weightage = 1;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public enum ApprovalStatus {
    	PENDING,APPROVED,REJECTED
    }
}
