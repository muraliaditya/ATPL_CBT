package com.example.cbt_system.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cbt_system.entity.Contest;
import com.example.cbt_system.entity.Question;
import com.example.cbt_system.repository.ContestRepository;
import com.example.cbt_system.repository.QuestionRepository;
import com.example.cbt_system.service.ContestService;

@RestController
@RequestMapping("/api/contests")
public class ContestController {

	private final ContestService contestService;
	private final QuestionRepository questionRepo;
	private final ContestRepository contestRepo;

	
	
	

	public ContestController(ContestService contestService, QuestionRepository questionRepo,
			ContestRepository contestRepo) {
		super();
		this.contestService = contestService;
		this.questionRepo = questionRepo;
		this.contestRepo = contestRepo;
	}

	@PostMapping
	public Contest createContest(@RequestBody Contest contest) {
		return contestService.createContest(contest);
	}
	
	@GetMapping
	public List<Contest> getAllContests() {
		return contestRepo.findAll();
	}
	
	@PostMapping("/{contestId}/add/{questionId}")
	public Contest addQuestion(@PathVariable Long contestId,@PathVariable Long questionId) {
		return contestService.addQuestionToContest(contestId, questionId);
	}
	
	
	@GetMapping("/random")
	public Question getRandom() {
		
		return questionRepo.findRandomApprovedQuestion();
		
	}
	
}
