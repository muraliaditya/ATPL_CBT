package com.aaslin.testcase.testcases.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aaslin.testcase.testcases.model.Testcase;
import com.aaslin.testcase.testcases.repository.TestcaseRepository;

@Service
public class TestcaseService {
	@Autowired
	private TestcaseRepository repo;

	public List<Testcase> getTestcases(){
		return repo.findAll();
	}
	public Optional<Testcase> getTestcaseId(String id){
		return repo.findById(id);
	}
	public Testcase createTestcase(Testcase testcase) {
		return repo.save(testcase);
	}
	public Optional<Testcase> updateTestcase(String id,Testcase testcase){
				return repo.findById(id).map(existing -> {
		            testcase.setTestcaseId(id);
		            return repo.save(testcase);
		        });
	}
	public void deleteTestcase(String id) {
		if(repo.existsById(id)) {
			repo.deleteById(id);
		}
	}
}

