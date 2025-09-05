package com.aaslin.cbt.service;

import com.aaslin.cbt.entity.MCQAnswers;
import com.aaslin.cbt.repo.MCQAnswersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MCQAnswersService {

    @Autowired
    private MCQAnswersRepository answersRepo;

    public List<MCQAnswers> getAllAnswers() {
        return answersRepo.findAll();
    }

    public Optional<MCQAnswers> getAnswerById(String id) {
        return answersRepo.findById(id);
    }

    public List<MCQAnswers> getAnswersByMcqId(String mcqId) {
        return answersRepo.findByMcqId(mcqId);
    }

    public MCQAnswers createAnswer(MCQAnswers answer) {
        return answersRepo.save(answer);
    }

    public Optional<MCQAnswers> updateAnswer(String id, MCQAnswers answer) {
        return answersRepo.findById(id).map(existing -> {
            answer.setMcqAnswerId(id);
            return answersRepo.save(answer);
        });
    }

    public void deleteAnswer(String id) {
        if (answersRepo.existsById(id)) {
            answersRepo.deleteById(id);
        }
    }
}