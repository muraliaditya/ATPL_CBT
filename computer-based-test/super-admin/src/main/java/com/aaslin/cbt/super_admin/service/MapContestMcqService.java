package com.aaslin.cbt.super_admin.service;
import com.aaslin.cbt.common.model.Contest;
import com.aaslin.cbt.common.model.MapContestMcq;
import com.aaslin.cbt.super_admin.idgenerator.MapIdGenerator;
import com.aaslin.cbt.super_admin.repository.MapContestMcqRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MapContestMcqService {

    private final MapContestMcqRepository mapRepo;

    public void mapMcqsToContest(Contest contest, List<MapContestMcq> mappings) {
        mapRepo.deleteByContest_ContestId(contest.getContestId());   
        Integer lastId = mapRepo.findMaxMapNumber();
        if (lastId == null) {
            lastId = 0;
        }

        for (MapContestMcq map : mappings) {           
            String newId = MapIdGenerator.generateMapId(++lastId);
            map.setContestMcqMapId(newId);

            map.setContest(contest);
            map.setCreatedAt(LocalDateTime.now());
        }    
        mapRepo.saveAll(mappings);
    }

    public List<MapContestMcq> getMcqsForContest(String contestId) {
        return mapRepo.findByContest_ContestId(contestId);
    }
}

