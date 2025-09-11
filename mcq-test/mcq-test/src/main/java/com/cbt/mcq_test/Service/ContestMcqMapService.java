package com.cbt.mcq_test.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cbt.mcq_test.Repository.ContestMcqMapRepository;
import com.cbt.mcq_test.Repository.ContestRepository;
import com.cbt.mcq_test.Repository.McqQuestionRepository;
import com.cbt.mcq_test.entity.Contest;
import com.cbt.mcq_test.entity.ContestMcqMap;
import com.cbt.mcq_test.entity.McqQuestion;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContestMcqMapService {

    private final ContestMcqMapRepository mapRepo;
    private final ContestRepository contestRepo;
    private final McqQuestionRepository mcqRepo;

    public ContestMcqMap assignQuestionToContest(String contestId, String mcqId, int weightage) {
        Contest contest = contestRepo.findById(contestId)
                .orElseThrow(() -> new RuntimeException("Contest not found"));

        McqQuestion mcq = mcqRepo.findById(mcqId)
                .orElseThrow(() -> new RuntimeException("MCQ not found"));

        ContestMcqMap mapping = new ContestMcqMap();
        String mapId = "MAP" + System.currentTimeMillis();
        mapping.setMapId(mapId);

        mapping.setContest(contest);
        mapping.setMcqQuestion(mcq);
        mapping.setWeightage(weightage);
        mapping.setAssignedAt(LocalDateTime.now());
        mapping.setCreatedAt(LocalDateTime.now());
        mapping.setUpdatedAt(LocalDateTime.now());

        return mapRepo.save(mapping);
    }
    
    public List<ContestMcqMap> getQuestionforSection(String Section){
    	return mapRepo.findBySection(Section);
    }

   
    public List<ContestMcqMap> getQuestionsForContest(String contestId) {
        return mapRepo.findByContest_ContestId(contestId);
    }
}
