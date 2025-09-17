package com.aaslin.cbt.participant.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.aaslin.cbt.common.model.Testcases;
import com.aaslin.cbt.participant.dto.CompileRunRequest;
import com.aaslin.cbt.participant.dto.CompileRunResponse;
import com.aaslin.cbt.participant.dto.TestcaseResultResponse;
import com.aaslin.cbt.participant.repository.TestCaseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompilerService {

	private final TestCaseRepository testcaseRepo;
	private final DockerExecutor dockerExecutor;
	
	public CompileRunResponse compileAndRun(CompileRunRequest request) {
		List<Testcases> testcases=testcaseRepo.findByCodingQuestion_CodingQuestionId(request.getQuestionId());
		List<TestcaseResultResponse> results=new ArrayList<>();
		int passedCount=0;
		for(Testcases tc : testcases) {
			String input=tc.getInputValues();
			String expected=tc.getExpectedOutput();
			String actualOutput;
			try {
				actualOutput=dockerExecutor.runCode(request.getLanguageType(),request.getCode(),input);
			}catch(Exception e) {
				return new CompileRunResponse("COMPILATION_ERROR", e.getMessage());
			}
			String status=expected.trim().equals(actualOutput.trim()) ? "PASSED" : "FAILED";
			if(status.equals("PASSED")) passedCount++;
			results.add(new TestcaseResultResponse(tc.getTestcaseId(),input,expected,actualOutput,status,tc.getWeightage()));
		}
		
		return new CompileRunResponse(
				passedCount==testcases.size() ? "PASSED" :"WRONG_ANSWER",
						"Compiled and executed successfully",
						passedCount,results);
	}
}
