package com.aaslin.cbt.participant.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.aaslin.cbt.common.model.Testcases;
import com.aaslin.cbt.participant.dto.CompileRunRequest;
import com.aaslin.cbt.participant.dto.CompileRunResponse;
import com.aaslin.cbt.participant.dto.SubmissionRequest;
import com.aaslin.cbt.participant.dto.SubmissionResponse;
import com.aaslin.cbt.participant.dto.TestcaseResultResponse;
import com.aaslin.cbt.participant.repository.TestCaseRepository;

import lombok.RequiredArgsConstructor;

@Service("ParticipantCompilerService")
@RequiredArgsConstructor
public class CompilerService {

    private final TestCaseRepository testcaseRepo;
    private final DockerExecutor dockerExecutor;

    public CompileRunResponse compileAndRun(CompileRunRequest request) {
        List<Testcases> testcases = testcaseRepo.findByCodingQuestion_CodingQuestionIdAndTestcaseType(
                request.getQuestionId(), Testcases.TestcaseType.PUBLIC);

        List<TestcaseResultResponse> results = new ArrayList<>();
        int passedCount = 0;

        for(Testcases tc : testcases) {
            String input = tc.getInputValues();
            String expected = tc.getExpectedOutput();
            String actualOutput;
            try {
                actualOutput = dockerExecutor.runTemporaryCode(request.getLanguageType(), request.getCode(), input);
            } catch(Exception e) {
                return new CompileRunResponse("COMPILATION_ERROR", e.getMessage(), 0, results);
            }
            String status = expected.trim().equals(actualOutput.trim()) ? "PASSED" : "FAILED";
            if(status.equals("PASSED")) passedCount++;
            results.add(new TestcaseResultResponse(tc.getTestcaseId(), input, expected, actualOutput, status, tc.getWeightage()));
        }

        return new CompileRunResponse(
                passedCount == testcases.size() ? "PASSED" : "WRONG_ANSWER",
                "Compiled and executed successfully",
                passedCount, results
        );
    }
    public SubmissionResponse submitCode(SubmissionRequest request) throws Exception {
        List<Testcases> publicTestcases = testcaseRepo.findByCodingQuestion_CodingQuestionIdAndTestcaseType(
                request.getQuestionId(), Testcases.TestcaseType.PUBLIC);
        List<Testcases> privateTestcases = testcaseRepo.findByCodingQuestion_CodingQuestionIdAndTestcaseType(
                request.getQuestionId(), Testcases.TestcaseType.PRIVATE);

        List<TestcaseResultResponse> publicResults = new ArrayList<>();
        List<TestcaseResultResponse> privateResults = new ArrayList<>();
        int publicPassed = 0;
        int privatePassed = 0;

        String savedFilePath = dockerExecutor.runAndSaveCode(
                request.getParticipantId(), 
                request.getQuestionId(), 
                request.getLanguageType(), 
                request.getCode(), 
                "D:/codes"
        );

	        for(Testcases tc : publicTestcases) {
	            String actualOutput = dockerExecutor.executeUserCode(request.getLanguageType(), request.getCode(), tc.getInputValues());
	            String status = tc.getExpectedOutput().trim().equals(actualOutput.trim()) ? "PASSED" : "FAILED";
	            if(status.equals("PASSED")) publicPassed++;
	            publicResults.add(new TestcaseResultResponse(tc.getTestcaseId(), tc.getInputValues(), tc.getExpectedOutput(), actualOutput, status, tc.getWeightage()));
	        }
	
	        for(Testcases tc : privateTestcases) {
	        	 String actualOutput = dockerExecutor.executeUserCode(request.getLanguageType(), request.getCode(), tc.getInputValues());
	            String status = tc.getExpectedOutput().trim().equals(actualOutput.trim()) ? "PASSED" : "FAILED";
	            if(status.equals("PASSED")) privatePassed++;
	            privateResults.add(new TestcaseResultResponse(tc.getTestcaseId(), null, null, null, status, tc.getWeightage()));
	        }
	
	        String overallStatus = (publicPassed == publicTestcases.size() && privatePassed == privateTestcases.size()) ? "SOLVED" : "PARTIALLY_SOLVED";
	        
	
	        return new SubmissionResponse(overallStatus, "Compiled and executed successfully",request.getCode(), publicPassed, privatePassed, publicResults, privateResults);
	    }
	}