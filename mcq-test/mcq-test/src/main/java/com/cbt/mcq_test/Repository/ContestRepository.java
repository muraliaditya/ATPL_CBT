package com.cbt.mcq_test.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cbt.mcq_test.entity.Contest;

@Repository
public interface ContestRepository extends JpaRepository<Contest,String> {

	
}

