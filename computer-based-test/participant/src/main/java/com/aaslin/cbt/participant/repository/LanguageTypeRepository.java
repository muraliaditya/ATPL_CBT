package com.aaslin.cbt.participant.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aaslin.cbt.common.model.LanguageType;

@Repository("ParticipantLanguageTypeRepository")
public interface LanguageTypeRepository extends JpaRepository<LanguageType,String> {

	Optional<LanguageType> findByLanguageType(String name);
}
