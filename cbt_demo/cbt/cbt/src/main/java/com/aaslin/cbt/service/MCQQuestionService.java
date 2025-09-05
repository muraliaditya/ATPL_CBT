package com.aaslin.cbt.service;

import com.aaslin.cbt.entity.MCQQuestion;
import com.aaslin.cbt.repo.MCQQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MCQQuestionService {

    @Autowired
    private MCQQuestionRepository questionRepo;

    public List<MCQQuestion> getAllQuestions() {
        return questionRepo.findAll();
    }

    public Optional<MCQQuestion> getQuestionById(String id) {
        return questionRepo.findById(id);
    }

    public MCQQuestion createQuestion(MCQQuestion question) {
        return questionRepo.save(question);
    }

    public Optional<MCQQuestion> updateQuestion(String id, MCQQuestion question) {
        return questionRepo.findById(id).map(existing -> {
            question.setMcqId(id);
            return questionRepo.save(question);
        });
    }

    public void deleteQuestion(String id) {
        if (questionRepo.existsById(id)) {
            questionRepo.deleteById(id);
        }
    }
}