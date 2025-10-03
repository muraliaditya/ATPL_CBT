package com.aaslin.cbt.super_admin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aaslin.cbt.common.model.Section;

@Repository
public interface SectionRepository extends JpaRepository<Section, String> {
    Optional<Section> findBySection(String section); 
    Optional<Section> findBySectionIgnoreCase(String section);
}
