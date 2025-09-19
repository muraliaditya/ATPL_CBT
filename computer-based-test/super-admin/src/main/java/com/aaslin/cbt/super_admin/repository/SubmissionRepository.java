package com.aaslin.cbt.super_admin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aaslin.cbt.common.model.Submission;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, String> {
	
    List<Submission> findByContestContestIdAndParticipantParticipantId(String contestId, String participantId);
    
    List<Submission> findByContestContestId(String contestId);
    
    Optional<Submission> findBySubmissionIdAndParticipant_ParticipantIdAndContest_ContestId(String submissionId,String participantId,String contestId);
}