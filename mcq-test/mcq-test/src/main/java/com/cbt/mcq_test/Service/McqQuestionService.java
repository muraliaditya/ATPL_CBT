package com.cbt.mcq_test.Service;
import org.springframework.stereotype.Service;

import com.cbt.mcq_test.Repository.McqQuestionRepository;
import com.cbt.mcq_test.entity.McqQuestion;
import com.cbt.mcq_test.entity.McqQuestion.Section;
import com.cbt.mcq_test.idgenerator.McqIdGenerator;

import java.util.List;

@Service
public class McqQuestionService {

    private final McqQuestionRepository mcqRepo;
      
    public McqQuestionService(McqQuestionRepository mcqRepo) {
        this.mcqRepo = mcqRepo;
    }

    public List<McqQuestion> getAllMcqs() {
        return mcqRepo.findAll();
    }

    public McqQuestion saveMcq(McqQuestion mcq) {
        return mcqRepo.save(mcq);
    }
    
    public McqQuestion addQuestion(McqQuestion mcq) {
        mcq.setMcqId(McqIdGenerator.generateMcqId());
        return mcqRepo.save(mcq);
    }
    
    public List<McqQuestion> addQuestions(List<McqQuestion> mcqs) {
        for (McqQuestion mcq : mcqs) {
            mcq.setMcqId(McqIdGenerator.generateMcqId());
        }
        return mcqRepo.saveAll(mcqs);
    }
    
    public List<McqQuestion> getQuestionsBySection(Section section) {
        return mcqRepo.findBySection(section);
    }
    
    public McqQuestion updateMcq(String id, McqQuestion updatedMcq) {
        McqQuestion existing = mcqRepo.findById(id).orElseThrow(() -> new RuntimeException("MCQ not found"));
        existing.setQuestionText(updatedMcq.getQuestionText());
        existing.setOption1(updatedMcq.getOption1());
        existing.setOption2(updatedMcq.getOption2());
        existing.setOption3(updatedMcq.getOption3());
        existing.setOption4(updatedMcq.getOption4());
        existing.setCorrectAnswer(updatedMcq.getCorrectAnswer());
        existing.setSection(updatedMcq.getSection());
        existing.setWeightage(updatedMcq.getWeightage());
        return mcqRepo.save(existing);
    }

    public void deleteMcq(String id) {
        mcqRepo.deleteById(id);
    }
}
