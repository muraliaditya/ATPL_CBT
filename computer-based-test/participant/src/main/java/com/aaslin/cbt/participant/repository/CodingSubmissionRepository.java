package com.aaslin.cbt.participant.repository;

import com.aaslin.cbt.common.model.CodingQuestions;
import com.aaslin.cbt.common.model.CodingSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CodingSubmissionRepository extends JpaRepository<CodingSubmission, String> { 

    List<CodingSubmission> findBySubmission_SubmissionId(String submissionId);

    @Query("SELECT c.codingSubmissionId FROM CodingSubmission c ORDER BY c.codingSubmissionId DESC")
    List<String> findAllCodingSubmissionIdsDesc();


    CodingSubmission findTopBySubmission_Participant_ParticipantIdAndCodingQuestion_CodingQuestionIdOrderByCreatedAtDesc(String participantId,String codingQuestionId);

	int countBySubmission_Participant_ParticipantIdAndCodingQuestion_CodingQuestionId(String participantId,
			String codingQuestionId);

	List<CodingSubmission> findBySubmission_Participant_ParticipantIdAndSubmission_Contest_ContestId(
			String participantId, String contestId);
}