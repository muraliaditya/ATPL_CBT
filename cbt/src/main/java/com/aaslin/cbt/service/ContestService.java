package com.aaslin.cbt.service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aaslin.cbt.entity.Contest;
import com.aaslin.cbt.repository.ContestRepository;

@Service
public class ContestService {

    @Autowired
    private ContestRepository contestRepository;

    public Contest createContest(Contest contest) {
    
        List<String> allIds = contestRepository.findAllContestIds();
        
        Set<Integer> existingNumbers = allIds.stream()
                .map(id -> {
                    try {
                        return Integer.parseInt(id.replace("CON", ""));
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                })
                .collect(Collectors.toSet());

        int newIdNumber = 1;
        while (existingNumbers.contains(newIdNumber)) {
            newIdNumber++;
        }
        contest.setContestId(String.format("CON%02d", newIdNumber));
        if (contest.getStartTime() != null && contest.getEndTime() != null) {
            Duration duration = Duration.between(contest.getStartTime(), contest.getEndTime());
            contest.setDuration(LocalTime.ofSecondOfDay(duration.getSeconds()));
        }
        return contestRepository.save(contest);
    }

    public List<Contest> getAllContests() {
        return contestRepository.findAll();
    }

    public Contest getContestById(String id) {
        return contestRepository.findById(id).orElse(null);
    }

    public Contest updateContest(String id, Contest contest) {
        Contest existing = getContestById(id);
        if (existing == null) return null;

        existing.setContestName(contest.getContestName());
        existing.setAllowedCollegeId(contest.getAllowedCollegeId());
        existing.setStartTime(contest.getStartTime());
        existing.setEndTime(contest.getEndTime());

        if (contest.getStartTime() != null && contest.getEndTime() != null) {
            Duration duration = Duration.between(contest.getStartTime(), contest.getEndTime());
            existing.setDuration(LocalTime.ofSecondOfDay(duration.getSeconds()));
        }

        existing.setTotalMcqQuestions(contest.getTotalMcqQuestions());
        existing.setTotalCodingQuestions(contest.getTotalCodingQuestions());
        existing.setCreatedBy(contest.getCreatedBy());
        existing.setStatus(contest.getStatus());

        return contestRepository.save(existing);
    }

    public boolean deleteContest(String id) {
        if (!contestRepository.existsById(id)) return false;
        contestRepository.deleteById(id);
        return true;
    }
}
