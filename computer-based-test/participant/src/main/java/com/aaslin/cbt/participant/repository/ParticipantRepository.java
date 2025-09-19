package com.aaslin.cbt.participant.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.aaslin.cbt.common.model.Participant;

public interface ParticipantRepository extends JpaRepository<Participant,String> {

}
