package com.aaslin.cbt.participant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aaslin.cbt.common.model.College;

@Repository
public interface CollegeRepository extends JpaRepository<College,String> {
	List<College> findByCollegeName(String collegeName);

}
