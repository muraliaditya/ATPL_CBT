package com.aaslin.cbt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aaslin.cbt.entity.Contest;

@Repository
public interface ContestRepository extends JpaRepository<Contest,String>{
	Optional<Contest> findByStatusAndAllowedCollegeUidsContaining(Contest.Status status, String collegeUid);
}
