package com.aaslin.cbt.participant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aaslin.cbt.common.model.CodingQuestions;

@Repository("participantCodingQuestionsRepository")
public interface CodingQuestionsRepository extends JpaRepository<CodingQuestions,String> {

}
