package com.example.cbt_system.service;

import org.springframework.stereotype.Service;
import com.example.cbt_system.entity.Contest;
import com.example.cbt_system.entity.Question;
import com.example.cbt_system.repository.ContestRepository;
import com.example.cbt_system.repository.QuestionRepository;

@Service
public class ContestService {

	private final ContestRepository contestRepo;
	private final QuestionRepository questionRepo;
	public ContestService(ContestRepository contestRepo, QuestionRepository questionRepo) {
		super();
		this.contestRepo = contestRepo;
		this.questionRepo = questionRepo;
	}
	
	public Contest createContest(Contest contest) {
		return contestRepo.save(contest);
	}
	
	public Contest addQuestionToContest(Long contestId,Long questionId) {
		Contest contest=contestRepo.findById(contestId).orElseThrow();
		Question question=questionRepo.findById(questionId).orElseThrow();
		if(!contest.getQuestions().contains(question)) {
		contest.getQuestions().add(question);
		}
		return contestRepo.save(contest);	
	}
	
	public Question randomQuestion() {
		return questionRepo.findRandomApprovedQuestion();
	}
}
