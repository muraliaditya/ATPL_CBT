package com.aaslin.cbt.common.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "participants_cbt")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Participant {
    @Id
    @Column(name = "participant_id", length = 50)
    private String participantId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "college_id")
    private College college;

    @Column(name = "college_regd_no")
    private String collegeRegdNo;

    @ManyToOne
    @JoinColumn(name = "designation_id")
    private Designation designation;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(name = "overall_experience")
    private String overallExperience;

    @Column(name = "highest_degree")
    private String highestDegree;

    @DecimalMin(value="0.00")
    @DecimalMax(value="100.00")
    @Column(name = "percentage", precision = 5, scale = 2)
    private BigDecimal percentage;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @ManyToOne
	@JoinColumn(name="contestId")
	private Contest contest;

    @OneToMany(mappedBy = "participant")
    private List<ParticipantStatus> participantStatuses;

    @OneToMany(mappedBy = "participant")
    private List<Submission> submissions;
}





