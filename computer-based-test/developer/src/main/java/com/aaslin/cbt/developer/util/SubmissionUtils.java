package com.aaslin.cbt.developer.util;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aaslin.cbt.common.model.DeveloperCodingSubmission;
import com.aaslin.cbt.common.model.DeveloperTestcaseResult;
import com.aaslin.cbt.common.model.Testcase;
import com.aaslin.cbt.developer.Dto.PrivateTestcaseResultDto;
import com.aaslin.cbt.developer.Dto.PublicTestcaseResultDto;
import com.aaslin.cbt.developer.repository.DeveloperTestcaseResultsRepository;
@Component
public class SubmissionUtils {
	
	@Autowired
	private DeveloperTestcaseResultsRepository resultRepo;
	public void saveErrorResult(DeveloperCodingSubmission submission, Testcase tc, String errorOutput) {
	    DeveloperTestcaseResult result = resultRepo
	            .findByDeveloperCodingSubmission_DeveloperCodingSubmissionIdAndTestcase_TestcaseId(
	                    submission.getDeveloperCodingSubmissionId(), tc.getTestcaseId())
	            .orElse(null);

	    if (result == null) {
	        String lastResultId = resultRepo.findTopByOrderByDeveloperTestcaseResultIdDesc()
	                .map(DeveloperTestcaseResult::getDeveloperTestcaseResultId)
	                .orElse(null);
	        String newResultId = CustomIdGenerator.generateNextId("DTCR", lastResultId);

	        result = new DeveloperTestcaseResult();
	        result.setDeveloperTestcaseResultId(newResultId);
	        result.setDeveloperCodingSubmission(submission);
	        result.setTestcase(tc);
	        result.setCreatedAt(LocalDateTime.now());
	    }

	    result.setTestcaseStatus(DeveloperTestcaseResult.DeveloperTestcaseStatus.FAILED);
	    result.setUpdatedAt(LocalDateTime.now());
	    resultRepo.save(result);
	}

	public void addResponseResult(Testcase tc, List<PublicTestcaseResultDto> publicResults,
	                               List<PrivateTestcaseResultDto> privateResults,
	                               String output, String status) {
	    if (tc.getTestcaseType() == Testcase.TestcaseType.PUBLIC) {
	        publicResults.add(new PublicTestcaseResultDto(
	                tc.getTestcaseId(),
	                tc.getInputValues(),
	                tc.getExpectedOutput(),
	                output,
	                status,
	                tc.getWeightage()
	        ));
	    } else {
	        privateResults.add(new PrivateTestcaseResultDto(
	                tc.getTestcaseId(),
	                status,
	                tc.getWeightage()
	        ));
	    }
	}

}

