package com.example.cbt_system.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.cbt_system.entity.Question;
import com.example.cbt_system.repository.QuestionRepository;
import com.example.cbt_system.service.CsvImportService;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {
	
//	private final QuestionRepository questionRepo;
//
//	public QuestionController(QuestionRepository questionRepo) {
//		super();
//		this.questionRepo = questionRepo;
//	}
//	
//	@PostMapping
//	public Question addQuestion(@RequestBody Question question) {
//		question.setStatus("pending");
//		return questionRepo.save(question);
//	}
//	
	@GetMapping
	public List<Question> getAllQuestions() {
		return questionRepo.findAll();
	}
//	
//	@PutMapping("/{id}/approve")
//	public Question approveQuestion(@PathVariable Long id) {
//		Question q=questionRepo.findById(id).orElseThrow();
//		q.setStatus("approved");
//		return questionRepo.save(q);
//		
//	}
//	
//	@DeleteMapping("/{id}/reject")
//	public String rejectQuestion(@PathVariable Long id) {
//		Question q=questionRepo.findById(id).orElseThrow();
//		questionRepo.delete(q);
//		return "deleted";
//	}
	

	    private final CsvImportService csvService;
	    private final QuestionRepository questionRepo;

	    public QuestionController(CsvImportService csvService, QuestionRepository questionRepo) {
	        this.csvService = csvService;
	        this.questionRepo = questionRepo;
	    }

	    // Import CSV
	    @PostMapping("/import")
	    public List<Question> uploadCsv(@RequestParam("file") MultipartFile file) {
	        return csvService.importQuestions(file);
	    }

	    // Get all pending
	    @GetMapping("/pending")
	    public List<Question> getPending() {
	        return questionRepo.findByStatus("pending");
	    }

	    // Approve
	    @PostMapping("/approve/{id}")
	    public Question approve(@PathVariable Long id) {
	        Question q = questionRepo.findById(id).orElseThrow();
	        q.setStatus("APPROVED");
	        return questionRepo.save(q);
	    }

	    // Reject
	    @PostMapping("/reject/{id}")
	    public Question reject(@PathVariable Long id) {
	        Question q = questionRepo.findById(id).orElseThrow();
	        q.setStatus("rejected");
	        return questionRepo.save(q);
	    }

	    // Get random approved
	    @GetMapping("/random-approved")
	    public Question getRandomApproved() {
	        return questionRepo.findRandomApprovedQuestion();
	    }
	
}
