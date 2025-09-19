package com.aaslin.cbt.participant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aaslin.cbt.common.model.Contest;

@Repository("ParticipantContestRepository")
public interface ContestRepository extends JpaRepository<Contest,String> {

}
