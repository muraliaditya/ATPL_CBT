package com.aaslin.cbt.common.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "companies_cbt")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Company {
    @Id
    @Column(name = "company_id", length = 50)
    private String companyId;

    @Column(name = "current_company_name", nullable = false)
    private String currentCompanyName;

    @OneToMany(mappedBy = "company")
    private List<Participant> participants;
}
