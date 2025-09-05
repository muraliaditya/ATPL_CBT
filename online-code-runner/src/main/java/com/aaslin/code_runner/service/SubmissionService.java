package com.aaslin.code_runner.service;

import com.aaslin.code_runner.RunRequest;
import com.aaslin.code_runner.RunResponse;
import com.aaslin.code_runner.dto.SubmissionRequest;
import com.aaslin.code_runner.dto.SubmissionResponse;
import com.aaslin.code_runner.entity.CodingSubmission;
import com.aaslin.code_runner.entity.CodingTestcase;
import com.aaslin.code_runner.repository.CodingSubmissionRepository;
import com.aaslin.code_runner.repository.CodingTestcaseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class SubmissionService {

    private final CodingSubmissionRepository repository;
    private final CodingTestcaseRepository testcaseRepository;
    private final DockerRunnerService dockerRunnerService;

    public SubmissionService(CodingSubmissionRepository repository,
                             CodingTestcaseRepository testcaseRepository,
                             DockerRunnerService dockerRunnerService) {
        this.repository = repository;
        this.testcaseRepository = testcaseRepository;
        this.dockerRunnerService = dockerRunnerService;
    }

    public SubmissionResponse submitCode(SubmissionRequest req) {
        List<CodingTestcase> testcases = testcaseRepository.findByCodingQuestionId(req.getCodingQuestionId());

        int totalScore = 0;
        List<String> passedIds = new ArrayList<>();
        List<SubmissionResponse.TestcaseResultDTO> results = new ArrayList<>();

        for (CodingTestcase tc : testcases) {
            RunRequest runReq = new RunRequest();
            runReq.setLanguage(RunRequest.Language.fromString(req.getLanguage()));
            runReq.setCode(req.getCode());
            runReq.setStdin(tc.getInput());

            RunResponse runRes = dockerRunnerService.run(runReq);

            String actual = runRes.getOutput().replaceAll("\\s+", "").trim();
            String expected = tc.getExpectedOutput().replaceAll("\\s+", "").trim();
            boolean passed = actual.equals(expected);

            if (passed) {
                totalScore += tc.getMarks();
                passedIds.add(tc.getTestcaseId());
            }

            SubmissionResponse.TestcaseResultDTO dto = new SubmissionResponse.TestcaseResultDTO();
            dto.setTestcaseId(tc.getTestcaseId());
            dto.setPassed(passed);
            dto.setMarksAwarded(passed ? tc.getMarks() : 0);
            results.add(dto);
        }

        CodingSubmission entity = new CodingSubmission();
        entity.setCodingSubmissionId(req.getCodingSubmissionId() != null ? req.getCodingSubmissionId() : UUID.randomUUID().toString());
        entity.setCodingQuestionId(req.getCodingQuestionId());
        entity.setLanguage(CodingSubmission.Language.valueOf(req.getLanguage().toUpperCase()));
        entity.setCode(req.getCode());
        entity.setScore(totalScore);
        entity.setTestcasesPassed(String.join(",", passedIds));
        entity.setFinal(req.isFinal());
        entity.setStatus(passedIds.size() == testcases.size() ? "SUCCESS" : "PARTIAL");
        entity.setSubmittedAt(LocalDateTime.now());

        repository.save(entity);

        SubmissionResponse res = new SubmissionResponse();
        res.setCodingSubmissionId(entity.getCodingSubmissionId());
        res.setCodingQuestionId(entity.getCodingQuestionId());
        res.setLanguage(entity.getLanguage().name());
        res.setCode(entity.getCode());
        res.setScore(entity.getScore());
        res.setTestCasesPassed(entity.getTestcasesPassed());
        res.setFinal(entity.isFinal());
        res.setStatus(entity.getStatus());
        res.setSubmittedAt(entity.getSubmittedAt());

        return res;
    }
}