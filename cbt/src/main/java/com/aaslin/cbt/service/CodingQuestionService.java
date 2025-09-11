package com.aaslin.cbt.service;

import com.aaslin.cbt.entity.CodingQuestion;
import com.aaslin.cbt.entity.Contest;
import com.aaslin.cbt.repository.CodingQuestionRepository;
import com.aaslin.cbt.repository.ContestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CodingQuestionService {

    @Autowired
    private CodingQuestionRepository codingRepo;

    @Autowired
    private ContestRepository contestRepo;

    // ✅ Create CodingQuestion with auto-id like CODQ01
    public CodingQuestion createCodingQuestion(String contestId, CodingQuestion coding) {
        Contest contest = contestRepo.findById(contestId)
                .orElseThrow(() -> new RuntimeException("Contest not found"));

        // Fetch all existing IDs
        List<String> allIds = codingRepo.findAllCodingIds();

        // Extract numbers from IDs
        Set<Integer> existingNumbers = allIds.stream()
                .map(id -> {
                    try {
                        return Integer.parseInt(id.replace("CODQ", ""));
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                })
                .collect(Collectors.toSet());

        // Find smallest available number
        int newIdNumber = 1;
        while (existingNumbers.contains(newIdNumber)) {
            newIdNumber++;
        }

        // Assign ID like CODQ01
        coding.setCodingQuestionId(String.format("CODQ%02d", newIdNumber));
        coding.setContestId(contestId);
        coding.setContest(contest);

        return codingRepo.save(coding);
    }

    // ✅ Get all coding Qs of a contest
    public List<CodingQuestion> getByContestId(String contestId) {
        return codingRepo.findAll().stream()
                .filter(c -> contestId.equals(c.getContestId()))
                .collect(Collectors.toList());
    }

    // ✅ Update coding question
    public CodingQuestion updateCoding(String codingId, CodingQuestion updated) {
        CodingQuestion existing = codingRepo.findById(codingId)
                .orElseThrow(() -> new RuntimeException("Coding Question not found"));

        existing.setQuestion(updated.getQuestion());
        existing.setDescription(updated.getDescription());
        existing.setDifficulty(updated.getDifficulty());
        existing.setInputType(updated.getInputType());
        existing.setOutputType(updated.getOutputType());

        return codingRepo.save(existing);
    }

    // ✅ Delete coding question
    public void deleteCoding(String codingId) {
        codingRepo.deleteById(codingId);
    }
}
