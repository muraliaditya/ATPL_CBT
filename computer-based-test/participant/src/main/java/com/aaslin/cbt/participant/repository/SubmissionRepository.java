package com.aaslin.cbt.participant.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aaslin.cbt.common.model.Contest;
import com.aaslin.cbt.common.model.Submission;

@Repository("ParticipantSubmissionRsepository")
public interface SubmissionRepository extends JpaRepository<Submission,String> {

	@Query(value="Select s.submissionId From Submission s ORDER BY s.submissionId DESC")
	List<String> findAllSubmissionIdsDesc();

	Optional<Submission> findByParticipant_ParticipantIdAndContest_ContestId(String participantId, String contestId);
}
