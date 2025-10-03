package com.aaslin.cbt.super_admin.service;

import java.util.List;

import com.aaslin.cbt.common.model.Contest;
import com.aaslin.cbt.common.model.MapContestCoding;
import com.aaslin.cbt.super_admin.exceptions.ContestNotFoundException;
import com.aaslin.cbt.super_admin.repository.MapContestCodingRepository;
import com.aaslin.cbt.super_admin.util.AuditHelper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class MapContestCodingService {

    private final MapContestCodingRepository mapCodingRepo;
    private final AuditHelper auditHelper;

    public void mapCodingsToContest(Contest contest, List<MapContestCoding> mappings) {
        mapCodingRepo.deleteByContest_ContestId(contest.getContestId());
        for (MapContestCoding map : mappings) {
            map.setContest(contest);
            map.setAssignedAt(LocalDateTime.now());
            map.setCreatedAt(LocalDateTime.now());
            auditHelper.applyAuditForMapContestCoding(map);
        }
        mapCodingRepo.saveAll(mappings);
    }

    public List<MapContestCoding> getCodingForContest(String contestId) {
        List<MapContestCoding> result = mapCodingRepo.findByContest_ContestId(contestId);
        if (result == null || result.isEmpty()) {
            throw new ContestNotFoundException("No coding mappings found for contest: " + contestId);
        }
        return result;
    }

    public Integer getLastMapNumber() {
        Integer max = mapCodingRepo.findMaxMapNumber();
        return (max != null) ? max : 0;
    }
}
