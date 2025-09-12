package com.aaslin.coding_cbt.Service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aaslin.coding_cbt.Entity.CodingQuestion;
import com.aaslin.coding_cbt.Entity.CodingTestCase;
import com.aaslin.coding_cbt.Repository.CodingQuestionRepository;
import com.aaslin.coding_cbt.Repository.TestCaseRepository;

@Service
public class TestCaseService {

    private final CodingQuestionRepository questionRepo;
    private final TestCaseRepository testCaseRepo;

    public TestCaseService(CodingQuestionRepository questionRepo, TestCaseRepository testCaseRepo) {
        this.questionRepo = questionRepo;
        this.testCaseRepo = testCaseRepo;
    }

    private String generateNewTestCaseId() {
        String lastId = testCaseRepo.findMaxTestCaseId();
        if (lastId == null) return "TC001";
        int num = Integer.parseInt(lastId.replace("TC", ""));
        return "TC" + String.format("%03d", num + 1);
    }

    @Transactional
    public CodingQuestion createQuestion(CodingQuestion cq) {
        CodingQuestion savedQuestion = questionRepo.save(cq);

        if (cq.getTestcases() != null) {
            for (CodingTestCase tc : cq.getTestcases()) {
                if(tc.getId() == null || tc.getId().isEmpty()) tc.setId(generateNewTestCaseId());
                if(tc.getType() == null) tc.setType(CodingTestCase.TestCaseType.HIDDEN);
                if(tc.getWeightage() == null) tc.setWeightage(0);
                tc.setQuestion(savedQuestion);
                testCaseRepo.save(tc);
            }
            savedQuestion.setTestcases(testCaseRepo.findByQuestion_Id(savedQuestion.getId()));
        }

        return savedQuestion;
    }

    @Transactional
    public CodingQuestion updateQuestion(String id, CodingQuestion cq) {
        CodingQuestion existing = questionRepo.findById(id)
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

        CodingQuestion updatedQuestion = questionRepo.save(existing);

        if(cq.getTestcases() != null) {
            testCaseRepo.deleteByQuestionId(id);

            for (CodingTestCase tc : cq.getTestcases()) {
                if(tc.getId() == null || tc.getId().isEmpty()) tc.setId(generateNewTestCaseId());
                if(tc.getType() == null) tc.setType(CodingTestCase.TestCaseType.HIDDEN);
                if(tc.getWeightage() == null) tc.setWeightage(0);
                tc.setQuestion(updatedQuestion);
                testCaseRepo.save(tc);
            }
            updatedQuestion.setTestcases(testCaseRepo.findByQuestion_Id(updatedQuestion.getId()));
        }

        return updatedQuestion;
    }

    public List<CodingQuestion> getAllQuestions() {
        List<CodingQuestion> questions = questionRepo.findAll();
        for (CodingQuestion q : questions) {
            q.setTestcases(testCaseRepo.findByQuestion_Id(q.getId()));
        }
        return questions;
    }

    public CodingQuestion getQuestionById(String id) {
        CodingQuestion question = questionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found: " + id));
        question.setTestcases(testCaseRepo.findByQuestion_Id(id));
        return question;
    }

    public List<CodingTestCase> getTestcasesByQuestionId(String questionId){
        return testCaseRepo.findByQuestion_Id(questionId);
    }

    @Transactional
    public void deleteQuestion(String id) {
        testCaseRepo.deleteByQuestionId(id);
        questionRepo.deleteById(id);
    }
}