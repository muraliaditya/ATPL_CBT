package com.aaslin.cbt.participant.util;

import java.util.List;

import org.springframework.stereotype.Component;

import com.aaslin.cbt.participant.repository.CodingSubmissionRepository;
import com.aaslin.cbt.participant.repository.SubmissionRepository;

@Component
public class CustomIdGenerator {

	private  final SubmissionRepository submissionRepository;
	private  final CodingSubmissionRepository codingSubmissionRepository;
	

	 public CustomIdGenerator(SubmissionRepository submissionRepository,
			CodingSubmissionRepository codingSubmissionRepository) {
		super();
		this.submissionRepository = submissionRepository;
		this.codingSubmissionRepository = codingSubmissionRepository;
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
}
