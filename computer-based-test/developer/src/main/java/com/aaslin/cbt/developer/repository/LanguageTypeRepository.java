package com.aaslin.cbt.developer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aaslin.cbt.common.model.LanguageType;

public interface LanguageTypeRepository extends JpaRepository<LanguageType,String>{
	Optional<LanguageType> findByLanguageType(String languageType);
}
