package com.aaslin.testcase.testcases.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aaslin.testcase.testcases.model.Testcase;
import com.aaslin.testcase.testcases.service.TestcaseService;

@RestController
@RequestMapping("/api/testcases")
public class TestcaseController {
	
	@Autowired
	TestcaseService service;

	@GetMapping("/")
	public List<Testcase> getTestcases(){
		return service.getTestcases();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Testcase> getById(@PathVariable String id){
		return ResponseEntity.of(service.getTestcaseId(id));
	}
	
	 @PostMapping("/")
	 public ResponseEntity<Testcase> createTestcase(@RequestBody Testcase testcase) {
	     return ResponseEntity.status(HttpStatus.CREATED).body(service.createTestcase(testcase));
	  
	 }
	@PutMapping("/{id}")
	    public ResponseEntity<Testcase> updateTestcase(@PathVariable String id, @RequestBody Testcase testcase) {
	        return ResponseEntity.of(service.updateTestcase(id, testcase));
	    }

	@DeleteMapping("/{id}")
	    public void deleteTestcase(@PathVariable String id) {
	      service.deleteTestcase(id);
	    }
}
