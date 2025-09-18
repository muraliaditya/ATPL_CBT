package com.aaslin.cbt.common.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "language_type_cbt")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor

public class LanguageType {
    @Id
    @Column(name = "language_type_id", length = 50)
    private String languageTypeId;

    @Column(name = "language_type", nullable = false)
    private String languageType;

    @OneToMany(mappedBy = "languageType")
    private List<CodingSubmission> codingSubmissions;

    @OneToMany(mappedBy = "languageType")
    private List<DeveloperCodingSubmissions> developerCodingSubmissions;
}
