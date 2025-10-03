package com.aaslin.cbt.common.model;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "mcq_answers_cbt")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class McqAnswer {
    @Id
    @Column(name = "mcq_answer_id", length = 50)
    private String mcqAnswerId;

    @ManyToOne
    @JoinColumn(name = "mcq_question_id")
    private McqQuestion mcqQuestion;

    @ManyToOne
    @JoinColumn(name = "submission_id")
    private Submission submission;

    @Column(name = "selected_option")
    private String selectedOption;

    @Column(name = "is_correct")
    private Boolean isCorrect;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
