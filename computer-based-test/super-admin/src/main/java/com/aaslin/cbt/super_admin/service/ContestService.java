package com.aaslin.cbt.super_admin.service;
import com.aaslin.cbt.common.model.*;
import com.aaslin.cbt.super_admin.dto.ContestDTO;
import com.aaslin.cbt.super_admin.dto.McqQuestionDTO;
import com.aaslin.cbt.super_admin.dto.McqSectionDTO;
import com.aaslin.cbt.super_admin.idgenerator.ContestIdGenerator;
import com.aaslin.cbt.super_admin.repository.ContestRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ContestService {

    private final ContestRepository contestRepo;
    private final McqQuestionService mcqService;
    private final MapContestMcqService mapService;   
    public String createContest(ContestDTO dto) {
        Contest contest = new Contest();
        Integer lastId = contestRepo.findMaxContestNumber();
        if (lastId == null) lastId = 0;
        contest.setContestId(ContestIdGenerator.generateContestId(lastId));
        contest.setContestName(dto.getContestName());
        contest.setStartTime(dto.getStartTime());
        contest.setEndTime(dto.getStartTime().plusMinutes(dto.getDuration()));
        contest.setDuration(dto.getDuration());
        contest.setStatus(dto.getStatus());
        contest.setCategory(dto.getCategory());
        contest.setCreatedAt(LocalDateTime.now());
        contest.setDeleted(false);
        contestRepo.save(contest);
        if (dto.getMcqSections() != null) {
            List<MapContestMcq> mappings = new ArrayList<>();
            for (McqSectionDTO sectionDTO : dto.getMcqSections()) {
                for (McqQuestionDTO mcqDTO : sectionDTO.getMcqQuestions()) {
                    McqQuestions mcq = mcqService.getMcqById(mcqDTO.getMcqId());
                    MapContestMcq map = new MapContestMcq();
                    map.setMcqQuestion(mcq);
                    map.setWeightage(mcqDTO.getWeightage());
                    map.setCreatedAt(LocalDateTime.now());
                    mappings.add(map);
                }
            }
            mapService.mapMcqsToContest(contest, mappings);
        }

        return "Contest created successfully";
    } 
 
    public String updateContest(String contestId, ContestDTO dto) {
        Contest contest = contestRepo.findById(contestId)
                .orElseThrow(() -> new RuntimeException("Contest not found"));

        contest.setContestName(dto.getContestName());
        contest.setStartTime(dto.getStartTime());
        contest.setEndTime(dto.getStartTime().plusMinutes(dto.getDuration()));
        contest.setDuration(dto.getDuration());
        contest.setStatus(dto.getStatus());
        contest.setCategory(dto.getCategory());
        contest.setUpdatedAt(LocalDateTime.now());
        contestRepo.save(contest);

       
        if (dto.getMcqSections() != null) {
            List<MapContestMcq> mappings = new ArrayList<>();
            for (McqSectionDTO sectionDTO : dto.getMcqSections()) {
                for (McqQuestionDTO mcqDTO : sectionDTO.getMcqQuestions()) {
                    McqQuestions mcq = mcqService.getMcqById(mcqDTO.getMcqId());
                    MapContestMcq map = new MapContestMcq();
                    map.setMcqQuestion(mcq);
                    map.setWeightage(mcqDTO.getWeightage());
                    map.setCreatedAt(LocalDateTime.now());
                    mappings.add(map);
                }
            }
            mapService.mapMcqsToContest(contest, mappings);
        }

        return "Contest updated successfully";
    }

 
    public String deleteContest(String contestId) {
        Contest contest = contestRepo.findById(contestId)
                .orElseThrow(() -> new RuntimeException("Contest not found"));

        contest.setDeleted(true);
        contest.setUpdatedAt(LocalDateTime.now());
        contestRepo.save(contest);

        mapService.mapMcqsToContest(contest, new ArrayList<>());
        return "Contest deleted successfully";
    }

    
    public List<ContestDTO> getAllContests() {
        List<Contest> contests = contestRepo.findByDeletedFalse();
        List<ContestDTO> dtos = new ArrayList<>();
        for (Contest contest : contests) {
            dtos.add(toDTO(contest));
        }
        return dtos;
    }

 
    public ContestDTO getContestById(String contestId) {
        Contest contest = contestRepo.findById(contestId)
                .orElseThrow(() -> new RuntimeException("Contest not found"));
        ContestDTO dto = toDTO(contest);
        List<MapContestMcq> mappings = mapService.getMcqsForContest(contestId);
        Map<String, McqSectionDTO> sectionMap = new HashMap<>();
        for (MapContestMcq map : mappings) {
            String sectionName = map.getMcqQuestion().getSection().getSection();
            McqSectionDTO sectionDTO = sectionMap.getOrDefault(sectionName, new McqSectionDTO());
            if (sectionDTO.getSection() == null) { 
                sectionDTO.setSection(sectionName);
                sectionDTO.setSectionWeightage(map.getWeightage()); 
                sectionDTO.setMcqQuestions(new ArrayList<>()); 
            }

            McqQuestionDTO mcqDTO = mcqService.mapToDTO(map.getMcqQuestion());
            sectionDTO.getMcqQuestions().add(mcqDTO);
            sectionMap.put(sectionName, sectionDTO);
        }
        dto.setMcqSections(new ArrayList<>(sectionMap.values()));
        return dto;
    }
   
    private ContestDTO toDTO(Contest contest) {
        ContestDTO dto = new ContestDTO();
        dto.setContestId(contest.getContestId());
        dto.setContestName(contest.getContestName());
        dto.setStartTime(contest.getStartTime());
        dto.setEndTime(contest.getStartTime().plusMinutes(contest.getDuration()));
        dto.setDuration(contest.getDuration());
        dto.setCategory(contest.getCategory());
        dto.setStatus(contest.getStatus());
        return dto;
    }
}


