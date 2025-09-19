package com.aaslin.cbt.common.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "developer_coding_submissions_cbt")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class DeveloperCodingSubmissions {
    @Id
    @Column(name = "developer_coding_submission_id", length = 50)
    private String developerCodingSubmissionId;

    @ManyToOne
    @JoinColumn(name = "coding_question_id")
    private CodingQuestions codingQuestion;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "language_type_id")
    private LanguageType languageTypeId;

    @Column(columnDefinition = "TEXT")
    private String code;

    @Column(name = "public_testcases_passed")
    private Integer publicTestcasesPassed;

    @Column(name = "private_testcases_passed")
    private Integer privateTestcasesPassed;

    @Column(name = "is_final_attempt")
    private Boolean isFinalAttempt;

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private DeveloperCodingSubmissionStatus codeStatus;

    @OneToMany(mappedBy = "developerCodingSubmission")
    private List<DeveloperTestcaseResults> developerTestcaseResults;

    public enum DeveloperCodingSubmissionStatus {
    	SOLVED, PARTIALLY_SOLVED, COMPILATION_ERROR, RUNTIME_ERROR, WRONG_ANSWER
    }
}
