package com.aaslin.cbt.super_admin.service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.aaslin.cbt.common.model.McqAnswer;
import com.aaslin.cbt.common.model.McqQuestions;
import com.aaslin.cbt.common.model.Participant;
import com.aaslin.cbt.common.model.Submission;
import com.aaslin.cbt.super_admin.dto.ResultResponseDTO;
import com.aaslin.cbt.super_admin.repository.McqAnswerRepository;
import com.aaslin.cbt.super_admin.repository.SubmissionRepository;

import lombok.RequiredArgsConstructor;
import com.aaslin.cbt.super_admin.dto.McqResponse;
import com.aaslin.cbt.super_admin.dto.McqSectionDTO;
import com.aaslin.cbt.super_admin.dto.McqQuestionAnswerDTO;
import com.aaslin.cbt.super_admin.dto.ParticipantInfoDTO;

@Service
@RequiredArgsConstructor
public class ResultService {

    private final SubmissionRepository submissionRepo;
    private final McqAnswerRepository mcqAnswerRepo;
    public ResultResponseDTO getResult(String submissionId, String participantId, String contestId) {
        Submission submission = submissionRepo.findBySubmissionIdAndParticipant_ParticipantIdAndContest_ContestId(
                        submissionId, participantId, contestId)
                .orElseThrow(() -> new RuntimeException("Result not found for given submission"));

        Participant participant = submission.getParticipant();

        ParticipantInfoDTO participantInfo = ParticipantInfoDTO.builder()
                .participantId(participant.getParticipantId())
                .username(participant.getName())
                .collegeRegdNo(participant.getCollegeRegdNo())
                .collegeName(participant.getCollege() != null ? participant.getCollege().getCollegeName() : null)
                .codingMarks(submission.getTotalCodingScore())
                .mcqMarks(submission.getTotalMcqScore())
                .totalMarks(submission.getTotalScore())
                .percentage(participant.getPercentage())
                .build();
        List<McqAnswer> mcqAnswers = mcqAnswerRepo.findBySubmission_SubmissionId(submissionId);   
        Map<String, List<McqQuestionAnswerDTO>> groupedBySection =
            mcqAnswers.stream()
                .map(answer -> {
                    McqQuestions q = answer.getMcqQuestion();     
                    String sectionName = (q.getSection() != null) ? q.getSection().getSection() : "Unknown";
                    return McqQuestionAnswerDTO.builder()
                            .mcqQuestionId(q.getMcqQuestionId())
                            .questionText(q.getQuestionText())
                            .option1(q.getOption1())
                            .option2(q.getOption2())
                            .option3(q.getOption3())
                            .option4(q.getOption4())
                            .answerKey(q.getAnswerKey())
                            .selectedAnswer(answer.getSelectedOption())
                            .isCorrect(answer.getIsCorrect())
                            .weightage(q.getWeightage())
                            .section(sectionName)
                            .build();
                })
                .collect(Collectors.groupingBy(McqQuestionAnswerDTO::getSection, Collectors.toList()));
        List<McqSectionDTO> sections = groupedBySection.entrySet().stream()
        		 .map(entry -> {
        		     List<McqQuestionAnswerDTO> answersInSection = entry.getValue();
        		     int totalWeight = answersInSection.stream()
        		             .filter(a -> a.getWeightage() != null)
        		             .mapToInt(a -> a.getWeightage())  
        		             .sum();

        		     return McqSectionDTO.builder()
        		             .section(entry.getKey())
        		             .sectionWeightage(totalWeight)
        		             .mcqQuestionAnswer(answersInSection)
        		             .build();
        		 })
        		 .collect(Collectors.toList());
        		McqResponse mcqResponse = McqResponse.builder()
        		.mcqSections(sections)
        		.build();

        		return ResultResponseDTO.builder()
        		.participantInfo(participantInfo)
        		.mcqs(mcqResponse)
        		.build();
    }

   
}


