package com.aaslin.cbt.developer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aaslin.cbt.common.model.Sections;

public interface SectionRepository extends JpaRepository<Sections,String> {
	Optional<Sections> findBySectionIgnoreCase(String section);
}
