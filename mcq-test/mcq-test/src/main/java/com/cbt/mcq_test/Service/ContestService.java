package com.cbt.mcq_test.Service;

import org.springframework.stereotype.Service;

import com.cbt.mcq_test.Repository.ContestRepository;
import com.cbt.mcq_test.entity.Contest;
import com.cbt.mcq_test.idgenerator.ContestIdGenerator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContestService {

    private final ContestRepository contestRepo;

    public Contest createContest(Contest contest) {
        contest.setContestId(ContestIdGenerator.generateContestId());
        return contestRepo.save(contest);
    }
}
