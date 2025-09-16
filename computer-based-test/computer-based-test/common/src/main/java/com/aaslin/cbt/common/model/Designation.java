package com.aaslin.cbt.common.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "designation_cbt")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Designation {
    @Id
    @Column(name = "designatin_id", length = 50)
    private String designationId;

    @Column(name = "designation_name", nullable = false)
    private String designationName;

    @OneToMany(mappedBy = "designation")
    private List<Participant> participants;
}
