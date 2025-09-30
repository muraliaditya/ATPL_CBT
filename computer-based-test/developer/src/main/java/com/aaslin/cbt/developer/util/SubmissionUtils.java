package com.aaslin.cbt.developer.util;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aaslin.cbt.common.model.DeveloperCodingSubmissions;
import com.aaslin.cbt.common.model.DeveloperTestcaseResults;
import com.aaslin.cbt.common.model.Testcases;
import com.aaslin.cbt.developer.Dto.PrivateTestcaseResultDto;
import com.aaslin.cbt.developer.Dto.PublicTestcaseResultDto;
import com.aaslin.cbt.developer.repository.DeveloperTestcaseResultsRepository;
@Component
public class SubmissionUtils {
	
	@Autowired
	private DeveloperTestcaseResultsRepository resultRepo;
	public void saveErrorResult(DeveloperCodingSubmissions submission, Testcases tc, String errorOutput) {
	    DeveloperTestcaseResults result = resultRepo
	            .findByDeveloperCodingSubmission_DeveloperCodingSubmissionIdAndTestcase_TestcaseId(
	                    submission.getDeveloperCodingSubmissionId(), tc.getTestcaseId())
	            .orElse(null);

	    if (result == null) {
	        String lastResultId = resultRepo.findTopByOrderByDeveloperTestcaseResultIdDesc()
	                .map(DeveloperTestcaseResults::getDeveloperTestcaseResultId)
	                .orElse(null);
	        String newResultId = CustomIdGenerator.generateNextId("DTCR", lastResultId);

	        result = new DeveloperTestcaseResults();
	        result.setDeveloperTestcaseResultId(newResultId);
	        result.setDeveloperCodingSubmission(submission);
	        result.setTestcase(tc);
	        result.setCreatedAt(LocalDateTime.now());
	    }

	    result.setTestcaseStatus(DeveloperTestcaseResults.DeveloperTestcaseStatus.FAILED);
	    result.setUpdatedAt(LocalDateTime.now());
	    resultRepo.save(result);
	}

	public void addResponseResult(Testcases tc, List<PublicTestcaseResultDto> publicResults,
	                               List<PrivateTestcaseResultDto> privateResults,
	                               String output, String status) {
	    if (tc.getTestcaseType() == Testcases.TestcaseType.PUBLIC) {
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

