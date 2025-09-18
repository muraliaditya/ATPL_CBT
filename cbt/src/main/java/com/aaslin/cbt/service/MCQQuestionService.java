package com.aaslin.cbt.service;

import com.aaslin.cbt.entity.MCQQuestion;
import com.aaslin.cbt.entity.Contest;
import com.aaslin.cbt.repository.MCQQuestionRepository;
import com.aaslin.cbt.repository.ContestRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MCQQuestionService {

    @Autowired
    private MCQQuestionRepository mcqRepo;

    @Autowired
    private ContestRepository contestRepo;

    public MCQQuestion createMcq(String contestId, MCQQuestion mcq) {
        Contest contest = contestRepo.findById(contestId)
                .orElseThrow(() -> new RuntimeException("Contest not found"));

        List<String> allIds = mcqRepo.findAllMcqIds();

        Set<Integer> existingNumbers = allIds.stream()
                .map(id -> {
                    try {
                        return Integer.parseInt(id.replace("MCQQ", ""));
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                })
                .collect(Collectors.toSet());

        int newIdNumber = 1;
        while (existingNumbers.contains(newIdNumber)) {
            newIdNumber++;
        }
        
        mcq.setMcqId(String.format("MCQQ%02d", newIdNumber));
        mcq.setContest(contest);

        return mcqRepo.save(mcq);
    }
    

    public List<MCQQuestion> getMcqsByContest(String contestId) {
        return mcqRepo.findByContest_ContestId(contestId);
    }

    public MCQQuestion updateMcq(String mcqId, MCQQuestion updatedMcq) {
        MCQQuestion existing = mcqRepo.findById(mcqId)
                .orElseThrow(() -> new RuntimeException("MCQ not found"));

        existing.setQuestion(updatedMcq.getQuestion());
        existing.setOptionA(updatedMcq.getOptionA());
        existing.setOptionB(updatedMcq.getOptionB());
        existing.setOptionC(updatedMcq.getOptionC());
        existing.setOptionD(updatedMcq.getOptionD());
        existing.setCorrectanswer(updatedMcq.getCorrectanswer());
        existing.setMarks(updatedMcq.getMarks());
        existing.setType(updatedMcq.getType());

        return mcqRepo.save(existing);
    }

    public void deleteMcq(String mcqId) {
        mcqRepo.deleteById(mcqId);
    }
}
