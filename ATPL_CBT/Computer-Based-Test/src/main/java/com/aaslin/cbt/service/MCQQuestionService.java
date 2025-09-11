package com.aaslin.cbt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aaslin.cbt.entity.MCQQuestion;
//import com.aaslin.cbt.repository.MCQQuestionRepository;

//@Service
//public class MCQQuestionService {
//	
//	@Autowired
//	private  MCQQuestionRepository mcqrepo;
//	
//	public List<MCQQuestion> getall(){
//		return mcqrepo.findAll();
//		}
//	
//	public MCQQuestion create(MCQQuestion mcq) {
//		return mcqrepo.save(mcq);
//	}
//	
//	public MCQQuestion getmcqbyid(Long id) {
//		return mcqrepo.findById(id).orElse(null);
//		
//	}
//
//	public MCQQuestion Edit(Long id,MCQQuestion mcq) {
//		MCQQuestion mcqquestion=getmcqbyid(id);
//		mcqquestion.setQuestion(mcq.getQuestion());
//		mcqquestion.setOptionA(mcq.getOptionA());
//		mcqquestion.setOptionB(mcq.getOptionB());
//		mcqquestion.setOptionC(mcq.getOptionC());
//		mcqquestion.setOptionD(mcq.getOptionD());
//		mcqquestion.setCorrectAnswer(mcq.getCorrectAnswer());
//		return mcqrepo.save(mcqquestion);
//	}
//	public void delete(Long id) {
//		mcqrepo.deleteById(id);
//	}
//	public List<MCQQuestion> getByContestId(String contestid) {
//	        return mcqrepo.findByContest_Contestid(contestid);
//	    }
//	}



