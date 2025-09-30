package com.aaslin.cbt.common.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sections_cbt")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Sections {
	
	 @Id
	 @Column(name = "section_id", length = 50)
	 private String sectionId;

	 @Column(name = "section", nullable = false, length = 100)
	 private String section;
	 
	 @Column(name = "is_active",nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
	 private Boolean isActive=true;
	 
	 @OneToMany(mappedBy = "section")
	 private List<McqQuestions> mcqQuestions;
}
