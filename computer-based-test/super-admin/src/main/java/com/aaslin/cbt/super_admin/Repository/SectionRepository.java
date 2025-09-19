package com.aaslin.cbt.super_admin.Repository;
import com.aaslin.cbt.common.model.Sections;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SectionRepository extends JpaRepository<Sections, String> {
    Optional<Sections> findBySection(String section); 
}
