package com.aaslin.cbt.developer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aaslin.cbt.common.model.Sections;

@Repository
public interface Sectionrepository extends JpaRepository<Sections, String> {
    Optional<Sections> findBySectionIgnoreCase(String section);
    
    Optional<Sections> findTopByOrderBySectionIdDesc();
}
