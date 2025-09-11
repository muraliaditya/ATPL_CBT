package com.aaslin.coding_cbt.Service;

import org.springframework.stereotype.Service;

import com.aaslin.coding_cbt.Entity.CodingQuestion;
import com.aaslin.coding_cbt.Repository.CodingQuestionRepository;

import java.util.List;

@Service
public class CodingQuestionService {

    private final CodingQuestionRepository repository;

    public CodingQuestionService(CodingQuestionRepository repository) {
        this.repository = repository;
    }

    public CodingQuestion createQuestion(CodingQuestion cq) {
        return repository.save(cq);
    }

    public CodingQuestion updateQuestion(String id, CodingQuestion cq) {
        CodingQuestion existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found: " + id));
        existing.setQuestion(cq.getQuestion());
        existing.setDescription(cq.getDescription());
        existing.setDifficulty(cq.getDifficulty());
        existing.setOutputFormat(cq.getOutputFormat());
        existing.setApprovalStatus(cq.getApprovalStatus());
        existing.setUpdatedAt(cq.getUpdatedAt());
        existing.setUpdatedBy(cq.getUpdatedBy());
        existing.setJavaBoilerplateCode(cq.getJavaBoilerplateCode());
        existing.setPythonBoilerplateCode(cq.getPythonBoilerplateCode());
        existing.setExecutionTimeLimit(cq.getExecutionTimeLimit());
        existing.setMemoryLimit(cq.getMemoryLimit());
        existing.setInputType(cq.getInputType());
        existing.setInputParams(cq.getInputParams());
        return repository.save(existing);
    }

    public List<CodingQuestion> getAllQuestions() {
        return repository.findAll();
    }

    public CodingQuestion getQuestionById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found: " + id));
    }

    public void deleteQuestion(String id) {
        repository.deleteById(id);
    }
}
