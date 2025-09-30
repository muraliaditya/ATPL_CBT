package com.aaslin.cbt.participant.util;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.aaslin.cbt.participant.repository.CodingSubmissionRepository;
import com.aaslin.cbt.participant.repository.McqAnswersRepository;
import com.aaslin.cbt.participant.repository.SubmissionRepository;
import com.aaslin.cbt.participant.repository.TestcaseResultRepository;

@Component
public class CustomIdGenerator {

	private  final SubmissionRepository submissionRepository;
	private  final CodingSubmissionRepository codingSubmissionRepository;
	private final McqAnswersRepository mcqAnswersRepository;
	private final TestcaseResultRepository testcaseRepo;
	


	 

	 public CustomIdGenerator(SubmissionRepository submissionRepository,
			CodingSubmissionRepository codingSubmissionRepository, McqAnswersRepository mcqAnswersRepository,
			TestcaseResultRepository testcaseRepo) {
		super();
		this.submissionRepository = submissionRepository;
		this.codingSubmissionRepository = codingSubmissionRepository;
		this.mcqAnswersRepository = mcqAnswersRepository;
		this.testcaseRepo = testcaseRepo;
	}

	 public  String generateSubmissionId() {
	    	List<String> ids=submissionRepository.findAllSubmissionIdsDesc();
	    	if(ids.isEmpty()) return "SUB001";
	    	String lastId=ids.get(0);
	    	int num=Integer.parseInt(lastId.replace("SUB",""));
	    	num++;
	    	return String.format("SUB%03d", num);
	    }
	    
	    public   String generateCodingSubmissionId() {
	    	List<String> ids=codingSubmissionRepository.findAllCodingSubmissionIdsDesc();
	    	if(ids.isEmpty()) return "CSUB001";
	    	String lastId=ids.get(0);
	    	int num=Integer.parseInt(lastId.replace("CSUB",""));
	    	num++;
	    	return String.format("CSUB%03d", num);
	    }

		public String generateAnswerId() {
			List<String> ids=mcqAnswersRepository.findAllmcqAnswerIdsDesc();
	    	if(ids.isEmpty()) return "MCQA001";
	    	String lastId=ids.get(0);
	    	int num=Integer.parseInt(lastId.replace("MCQA",""));
	    	num++;
	    	return String.format("MCQA%03d", num);
		}
		
		public String generateTestcaseId() {
			return "TCR"+UUID.randomUUID().toString().replace("-","").substring(0, 3);	
		}
}
