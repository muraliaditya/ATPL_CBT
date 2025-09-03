package com.aaslin.cbt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aaslin.cbt.entity.CodingQuestion;
import com.aaslin.cbt.repository.CodingQuestionRepository;

@Service
public class CodingQuestionService {
	
	@Autowired
	private CodingQuestionRepository codingrepo;
	
	public List<CodingQuestion> getall(){
		return codingrepo.findAll();
		}
	
	public CodingQuestion create(CodingQuestion coding) {
		return codingrepo.save(coding);
	}
	
	public CodingQuestion getmcqbyid(Long id) {
		return codingrepo.findById(id).orElse(null);
		
	}

	public CodingQuestion Edit(Long id,CodingQuestion coding) {
		CodingQuestion codingquestion=getmcqbyid(id);
		codingquestion.setQuestiontext(coding.getQuestiontext());
		codingquestion.setInputType(coding.getInputType());
		codingquestion.setOutputtype(coding.getOutputtype());
		codingquestion.setDescrpition(coding.getDescrpition());
		codingquestion.setDifficulty(coding.getDifficulty());
		return codingrepo.save(codingquestion);
	}

	public void delete(Long id) {
		codingrepo.deleteById(id);
	}
}
	


