package com.aaslin.cbt.common.model;

import java.util.List;
import lombok.*;
import jakarta.persistence.*;

@Entity
@Table(name = "colleges_cbt")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class College {
    @Id
    @Column(name = "college_id", length = 50)
    private String collegeId;

    @Column(name = "college_name", unique=true,nullable = false)
    private String collegeName;

    @OneToMany(mappedBy = "college")
    private List<Participant> participants;
}

