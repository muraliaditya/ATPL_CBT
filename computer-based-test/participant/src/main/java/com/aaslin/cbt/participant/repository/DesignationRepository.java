package com.aaslin.cbt.participant.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aaslin.cbt.common.model.Designation;

public interface DesignationRepository extends JpaRepository<Designation,String> {
	
	Optional<Designation> findByDesignationName(String designationName);

}
