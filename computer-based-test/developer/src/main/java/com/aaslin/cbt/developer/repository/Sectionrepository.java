package com.aaslin.cbt.developer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aaslin.cbt.common.model.Section;

@Repository
public interface Sectionrepository extends JpaRepository<Section, String> {
    Optional<Section> findBySectionIgnoreCase(String section);
    
    Optional<Section> findTopByOrderBySectionIdDesc();
}
