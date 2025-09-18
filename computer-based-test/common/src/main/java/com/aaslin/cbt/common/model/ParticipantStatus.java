package com.aaslin.cbt.common.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "participant_status_cbt")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ParticipantStatus {
    @Id
    @Column(name = "participant_status_id", length = 50)
    private String participantStatusId;

    @ManyToOne
    @JoinColumn(name = "contest_id")
    private Contest contest;

    @ManyToOne
    @JoinColumn(name = "participant_id")
    private Participant participant;

    @Column(name = "start_time")
    private LocalDateTime startTime;
}
