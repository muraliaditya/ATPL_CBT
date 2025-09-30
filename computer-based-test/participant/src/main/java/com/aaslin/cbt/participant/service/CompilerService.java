package com.aaslin.cbt.participant.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aaslin.cbt.common.model.CodingSubmission;
import com.aaslin.cbt.common.model.Testcases;
import com.aaslin.cbt.participant.dto.CompileRunRequest;
import com.aaslin.cbt.participant.dto.CompileRunResponse;
import com.aaslin.cbt.participant.dto.SubmissionRequest;
import com.aaslin.cbt.participant.dto.SubmissionResponse;
import com.aaslin.cbt.participant.dto.TestcaseResultResponse;
import com.aaslin.cbt.participant.repository.CodingSubmissionRepository;
import com.aaslin.cbt.participant.repository.TestCaseRepository;
import com.aaslin.cbt.participant.util.CustomIdGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service("ParticipantCompilerService")
@RequiredArgsConstructor
public class CompilerService {

    private final TestCaseRepository testcaseRepo;
    private final CodingSubmissionRepository codingSubmissionRepository;
    private final DockerExecutor dockerExecutor;

    private static final int MAX_ATTEMPTS = 50;

    @Autowired
    private CustomIdGenerator customIdGenerator;

    private static final ObjectMapper mapper = new ObjectMapper();

    public CompileRunResponse compileAndRun(CompileRunRequest request) {
        List<Testcases> testcases = testcaseRepo.findByCodingQuestion_CodingQuestionIdAndTestcaseType(
                request.getQuestionId(), Testcases.TestcaseType.PUBLIC);

        List<TestcaseResultResponse> results = new ArrayList<>();
        int passedCount = 0;

        for (Testcases tc : testcases) {
            try {
                String stdin = flattenInput(tc.getInputValues());
                String actualOutput = dockerExecutor.runTemporaryCode(request.getLanguageType(), request.getCode(), stdin);
                boolean passed = tc.getExpectedOutput().trim().equals(actualOutput.trim());
                System.out.println("Passed"+passed);
                if (passed) passedCount++;
                results.add(new TestcaseResultResponse(tc.getTestcaseId(), tc.getInputValues(),
                        tc.getExpectedOutput(), actualOutput, passed ? "PASSED" : "FAILED", tc.getWeightage()));
            } catch (Exception e) {
                return new CompileRunResponse("COMPILATION_ERROR", e.getMessage(), 0, results);
            }
        }

        return new CompileRunResponse(
                passedCount == testcases.size() ? "PASSED" : "WRONG_ANSWER",
                "Compiled and executed successfully",
                passedCount, results
        );
    }

    public SubmissionResponse submitCode(SubmissionRequest request) throws Exception {

        String participantId = request.getParticipantId();
        String questionId = request.getQuestionId();

        int attemptNumber = codingSubmissionRepository
                .countBySubmission_Participant_ParticipantIdAndCodingQuestion_CodingQuestionId(participantId, questionId);
        boolean finalAttempt = (attemptNumber + 1 == MAX_ATTEMPTS);

        String codingSubmissionId;
        if (finalAttempt) {
            codingSubmissionId = customIdGenerator.generateCodingSubmissionId();
        } else {
            CodingSubmission latestSubmission = codingSubmissionRepository
                    .findTopBySubmission_Participant_ParticipantIdAndCodingQuestion_CodingQuestionIdOrderByCreatedAtDesc(
                            participantId, questionId);
            codingSubmissionId = (latestSubmission != null)
                    ? latestSubmission.getCodingSubmissionId()
                    : customIdGenerator.generateCodingSubmissionId();
        }

        List<Testcases> publicTestcases = testcaseRepo.findByCodingQuestion_CodingQuestionIdAndTestcaseType(
                questionId, Testcases.TestcaseType.PUBLIC);
        List<Testcases> privateTestcases = testcaseRepo.findByCodingQuestion_CodingQuestionIdAndTestcaseType(
                questionId, Testcases.TestcaseType.PRIVATE);

        List<TestcaseResultResponse> publicResults = new ArrayList<>();
        List<TestcaseResultResponse> privateResults = new ArrayList<>();
        int publicPassed = 0;
        int privatePassed = 0;
        int score = 0;

        String savedFilePath = dockerExecutor.runAndSaveCode(
                participantId,
                questionId,
                request.getLanguageType(),
                request.getCode()
        );

        Function<String, String> normalizeOutput = output -> {
            if (output == null) return "";
            String cleaned = output.trim().replaceAll("[\\r\\n]+", "");
            if ((cleaned.startsWith("\"") && cleaned.endsWith("\"")) ||
                (cleaned.startsWith("'") && cleaned.endsWith("'"))) {
                cleaned = cleaned.substring(1, cleaned.length() - 1);
            }
            return cleaned;
        };

        for (Testcases tc : publicTestcases) {
            String stdin = flattenInput(tc.getInputValues());
            String actualOutput = dockerExecutor.runTemporaryCode(request.getLanguageType(), request.getCode(), stdin);
            boolean passed = normalizeOutput.apply(actualOutput).equals(normalizeOutput.apply(tc.getExpectedOutput()));

            if (passed) {
                publicPassed++;
                score += tc.getWeightage();
            }

            publicResults.add(new TestcaseResultResponse(tc.getTestcaseId(), tc.getInputValues(),
                    tc.getExpectedOutput(), actualOutput, passed ? "PASSED" : "FAILED", tc.getWeightage()));
        }

        for (Testcases tc : privateTestcases) {
            String stdin = flattenInput(tc.getInputValues());
            String actualOutput = dockerExecutor.runTemporaryCode(request.getLanguageType(), request.getCode(), stdin);
            boolean passed = normalizeOutput.apply(actualOutput).equals(normalizeOutput.apply(tc.getExpectedOutput()));

            if (passed) {
                privatePassed++;
                score += tc.getWeightage();
            }

            privateResults.add(new TestcaseResultResponse(tc.getTestcaseId(), null, null, null,
                    passed ? "PASSED" : "FAILED", tc.getWeightage()));
        }

        String overallStatus = (publicPassed == publicTestcases.size() && privatePassed == privateTestcases.size())
                ? "SOLVED" : "PARTIALLY_SOLVED";

        return new SubmissionResponse(overallStatus, "Compiled and executed successfully", request.getCode(),
                publicPassed, privatePassed, score, codingSubmissionId, publicResults, privateResults);
    }

    private String flattenInput(String rawInput) throws Exception {
        StringBuilder sb = new StringBuilder();
        try {
            Object obj = mapper.readValue(rawInput, Object.class);
            collectValues(obj, sb);
        } catch (Exception e) {
            sb.append(rawInput).append("\n");
        }
        return sb.toString();
    }

    private void collectValues(Object obj, StringBuilder sb) {
        if (obj instanceof List) {
            for (Object v : (List<?>) obj) collectValues(v, sb);
        } else if (obj instanceof Map) {
            for (Object v : ((Map<?, ?>) obj).values()) collectValues(v, sb);
        } else if (obj != null) {
            sb.append(obj).append("\n");
        }
    }
}