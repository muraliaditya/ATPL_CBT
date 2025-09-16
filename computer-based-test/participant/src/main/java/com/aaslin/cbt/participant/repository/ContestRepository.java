package com.aaslin.cbt.participant.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aaslin.cbt.common.model.Contest;



public interface ContestRepository extends JpaRepository<Contest,String> {

}
