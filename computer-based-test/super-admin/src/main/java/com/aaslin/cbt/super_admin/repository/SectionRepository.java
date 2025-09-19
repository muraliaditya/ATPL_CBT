package com.aaslin.cbt.super_admin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aaslin.cbt.common.model.Sections;

@Repository
public interface SectionRepository extends JpaRepository<Sections, String> {
    Optional<Sections> findBySection(String section); 
    Optional<Sections> findBySectionIgnoreCase(String section);
}
