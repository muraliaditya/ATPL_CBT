package com.aaslin.cbt.super_admin.service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import com.aaslin.cbt.common.model.*;
import com.aaslin.cbt.super_admin.dto.*;
import com.aaslin.cbt.super_admin.exceptions.CategoryNotFoundException;
import com.aaslin.cbt.super_admin.exceptions.ContestNotFoundException;
import com.aaslin.cbt.super_admin.exceptions.CodingQuestionNotFoundException;

import com.aaslin.cbt.common.model.Category;
import com.aaslin.cbt.common.model.CodingQuestion;
import com.aaslin.cbt.common.model.Contest;
import com.aaslin.cbt.common.model.MapContestCoding;
import com.aaslin.cbt.common.model.MapContestMcq;
import com.aaslin.cbt.common.model.McqQuestion;
import com.aaslin.cbt.common.model.Testcase;
import com.aaslin.cbt.super_admin.dto.CodingQuestionRequest;
import com.aaslin.cbt.super_admin.dto.ContestDTO;
import com.aaslin.cbt.super_admin.dto.McqMappingDTO;
import com.aaslin.cbt.super_admin.dto.McqQuestionDTO;
import com.aaslin.cbt.super_admin.dto.McqSectionDTO;
import com.aaslin.cbt.super_admin.dto.PaginatedContestResponse;
import com.aaslin.cbt.super_admin.dto.TestcaseRequest;

import com.aaslin.cbt.super_admin.idgenerator.ContestIdGenerator;
import com.aaslin.cbt.super_admin.repository.CategoryRepository;
import com.aaslin.cbt.super_admin.repository.ContestRepository;
import com.aaslin.cbt.super_admin.util.AuditHelper;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContestService {

    private final ContestRepository contestRepo;
    private final McqQuestionService mcqService;
    private final MapContestMcqService mapService;
    private final CategoryRepository categoryRepo;
    private final CodingQuestionsService codingQuestionsService;
    private final MapContestCodingService mapCodingService;
    private final AuditHelper auditHelper;

    public PaginatedContestResponse searchContests(String contestName, int page, int size) {
        int pageIndex = (page > 0) ? page - 1 : 0;
        Page<Contest> contestsPage = contestRepo.findByContestNameContainingIgnoreCaseAndDeletedFalse(
                contestName, PageRequest.of(pageIndex, size));

        List<ContestDTO> contestDTOs = contestsPage.getContent().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());

        return new PaginatedContestResponse(
                page,
                size,
                contestsPage.getTotalElements(),
                contestsPage.getTotalPages(),
                contestDTOs
        );
    }

    public String createContest(ContestDTO dto) {
        Contest contest = new Contest();
        Integer lastId = contestRepo.findMaxContestNumber();
        if (lastId == null) lastId = 0;

        contest.setContestId(ContestIdGenerator.generateContestId(lastId));
        contest.setContestName(dto.getContestName());
        contest.setStartTime(dto.getStartTime());
        contest.setEndTime(dto.getStartTime().plusMinutes(dto.getDuration()));
        contest.setDuration(dto.getDuration());
        contest.setTotalCodingQuestions(dto.getTotalCodingQuestions());
        contest.setTotalQuantsMcqs(dto.getTotalQuantsMcqs());
        contest.setTotalTechnicalMcqs(dto.getTotalTechnicalMcqs());
        contest.setTotalVerbalMcqs(dto.getTotalVerbalMcqs());
        contest.setTotalReasoningMcqs(dto.getTotalReasoningMcqs());
        contest.setStatus(dto.getStatus());
        contest.setCreatedAt(LocalDateTime.now());
        contest.setDeleted(false);

        if (dto.getCategoryName() != null) {
            Category category = categoryRepo.findByCategoryName(dto.getCategoryName())
                    .orElseThrow(() -> new CategoryNotFoundException("Category not found: " + dto.getCategoryName()));
            contest.setCategory(category);
        }

        auditHelper.applyAuditForContest(contest);
        contestRepo.save(contest);

        if (dto.getMcqSections() != null) {
            List<MapContestMcq> mappings = new ArrayList<>();
            for (McqSectionDTO sectionDTO : dto.getMcqSections()) {
                for (McqMappingDTO mcqDTO : sectionDTO.getMcqQuestion()) {
                    McqQuestion mcq = mcqService.getMcqById(mcqDTO.getMcqQuestionId());
                    MapContestMcq map = new MapContestMcq();
                    map.setContest(contest);
                    map.setMcqQuestion(mcq);
                    map.setWeightage(mcqDTO.getWeightage());
                    map.setCreatedAt(LocalDateTime.now());
                    mappings.add(map);
                }
            }
            mapService.mapMcqsToContest(contest, mappings);
        }

        if (dto.getCodingQuestions() != null) {
            List<MapContestCoding> codingMappings = new ArrayList<>();
            Integer lastMapNumber = mapCodingService.getLastMapNumber();

            for (CodingQuestionRequest cqReq : dto.getCodingQuestions()) {
                if (cqReq.getCodingQuestionId() == null) {
                    throw new CodingQuestionNotFoundException("codingQuestionId is required for mapping.");
                }

                CodingQuestion cq = codingQuestionsService.getById(cqReq.getCodingQuestionId());


                MapContestCoding map = new MapContestCoding();
                String mapId = "MCC" + (++lastMapNumber);
                map.setContestCodingMapId(mapId);
                map.setContest(contest);
                map.setCodingQuestion(cq);
                map.setWeightage(cqReq.getWeightage() != null ? cqReq.getWeightage() : 0);
                map.setExecutionTimeLimit(cq.getExecutionTimeLimit());
                map.setMemoryLimit(cq.getMemoryLimit());
                map.setCreatedAt(LocalDateTime.now());
                codingMappings.add(map);
            }
            mapCodingService.mapCodingsToContest(contest, codingMappings);
        }

        return "Contest created successfully";
    }

    @Transactional
    public String updateContest(String contestId, ContestDTO dto) {
        Contest contest = contestRepo.findById(contestId)
                .orElseThrow(() -> new ContestNotFoundException("Contest not found: " + contestId));

        contest.setContestName(dto.getContestName());
        contest.setStartTime(dto.getStartTime());
        contest.setEndTime(dto.getStartTime().plusMinutes(dto.getDuration()));
        contest.setDuration(dto.getDuration());
        contest.setStatus(dto.getStatus());
        contest.setUpdatedAt(LocalDateTime.now());
        contest.setTotalCodingQuestions(dto.getTotalCodingQuestions());
        contest.setTotalQuantsMcqs(dto.getTotalQuantsMcqs());
        contest.setTotalTechnicalMcqs(dto.getTotalTechnicalMcqs());
        contest.setTotalVerbalMcqs(dto.getTotalVerbalMcqs());
        contest.setTotalReasoningMcqs(dto.getTotalReasoningMcqs());

        if (dto.getCategoryName() != null) {
            Category category = categoryRepo.findByCategoryName(dto.getCategoryName())
                    .orElseThrow(() -> new CategoryNotFoundException("Category not found: " + dto.getCategoryName()));
            contest.setCategory(category);
        }

        auditHelper.applyAuditForContest(contest);
        contestRepo.save(contest);

        if (dto.getMcqSections() != null) {
            List<MapContestMcq> mappings = new ArrayList<>();
            for (McqSectionDTO sectionDTO : dto.getMcqSections()) {
                for (McqMappingDTO mcqDTO : sectionDTO.getMcqQuestion()) {
                    McqQuestion mcq = mcqService.getMcqById(mcqDTO.getMcqQuestionId());
                    MapContestMcq map = new MapContestMcq();
                    map.setContest(contest);
                    map.setMcqQuestion(mcq);
                    map.setWeightage(mcqDTO.getWeightage());
                    map.setCreatedAt(LocalDateTime.now());
                    mappings.add(map);
                }
            }
            mapService.mapMcqsToContest(contest, mappings);
        }

        if (dto.getCodingQuestions() != null) {
            List<MapContestCoding> codingMappings = new ArrayList<>();
            Integer lastMapNumber = mapCodingService.getLastMapNumber();

            for (CodingQuestionRequest cqReq : dto.getCodingQuestions()) {
                if (cqReq.getCodingQuestionId() == null) {
                    throw new CodingQuestionNotFoundException("codingQuestionId is required for mapping.");
                }

                CodingQuestion cq = codingQuestionsService.getById(cqReq.getCodingQuestionId());


                MapContestCoding map = new MapContestCoding();
                String mapId = "MCC" + (++lastMapNumber);
                map.setContestCodingMapId(mapId);
                map.setContest(contest);
                map.setCodingQuestion(cq);
                map.setWeightage(cqReq.getWeightage() != null ? cqReq.getWeightage() : 0);
                map.setExecutionTimeLimit(cq.getExecutionTimeLimit());
                map.setMemoryLimit(cq.getMemoryLimit());
                map.setCreatedAt(LocalDateTime.now());
                codingMappings.add(map);
            }
            mapCodingService.mapCodingsToContest(contest, codingMappings);
        }

        return "Contest updated successfully";
    }

    @Transactional
    public String deleteContest(String contestId) {
        Contest contest = contestRepo.findById(contestId)
                .orElseThrow(() -> new ContestNotFoundException("Contest not found: " + contestId));

        contest.setDeleted(true);
        contest.setUpdatedAt(LocalDateTime.now());
        contestRepo.save(contest);

        mapService.mapMcqsToContest(contest, new ArrayList<>());

        return "Contest deleted successfully";
    }

    public List<ContestDTO> getAllContests() {
        return contestRepo.findByDeletedTrue()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ContestDTO getContestById(String contestId) {
        Contest contest = contestRepo.findById(contestId)
                .orElseThrow(() -> new ContestNotFoundException("Contest not found: " + contestId));

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
            McqQuestion mcq = map.getMcqQuestion();
            McqQuestionDTO mcqDTO = new McqQuestionDTO();
            mcqDTO.setMcqId(mcq.getMcqQuestionId());
            mcqDTO.setQuestionText(mcq.getQuestionText());
            mcqDTO.setOption1(mcq.getOption1());
            mcqDTO.setOption2(mcq.getOption2());
            mcqDTO.setOption3(mcq.getOption3());
            mcqDTO.setOption4(mcq.getOption4());
            mcqDTO.setCorrectAnswer(mcq.getAnswerKey());
            mcqDTO.setWeightage(map.getWeightage());
            sectionDTO.getMcqQuestions().add(mcqDTO);
            sectionMap.put(sectionName, sectionDTO);
        }
        dto.setMcqSections(new ArrayList<>(sectionMap.values()));

        List<MapContestCoding> codingMappings = mapCodingService.getCodingForContest(contestId);
        List<CodingQuestionRequest> codingReqs = new ArrayList<>();
        for (MapContestCoding map : codingMappings) {
            CodingQuestion cq = map.getCodingQuestion();
            CodingQuestionRequest req = new CodingQuestionRequest();
            req.setCodingQuestionId(cq.getCodingQuestionId());
            req.setQuestion(cq.getQuestion());
            req.setDescription(cq.getDescription());
            req.setDifficulty(cq.getDifficulty() != null ? cq.getDifficulty().name() : "EASY");
            req.setMethodName(cq.getMethodName());
            req.setJavaBoilerCode(cq.getJavaBoilerCode());
            req.setPythonBoilerCode(cq.getPythonBoilerCode());
            req.setParameterNames(codingQuestionsService.deserializeList(cq.getInputParams()));
            req.setInputTypes(codingQuestionsService.deserializeList(cq.getInputType()));
            req.setIsActive(cq.getIsActive());
            req.setWeightage(map.getWeightage());

            List<Testcase> testcases = cq.getTestcases();
            if (testcases != null) {
                List<TestcaseRequest> testcaseRequests = new ArrayList<>();
                for (Testcase testcase : testcases) {
                    TestcaseRequest testcaseRequest = new TestcaseRequest();
                    testcaseRequest.setTestcaseId(testcase.getTestcaseId());
                    testcaseRequest.setInputs(testcase.getInputValues());
                    testcaseRequest.setOutput(testcase.getExpectedOutput());
                    testcaseRequests.add(testcaseRequest);
                }
                req.setTestcases(testcaseRequests);
            }
            codingReqs.add(req);
        }
        dto.setCodingQuestions(codingReqs);

        return dto;
    }

    private ContestDTO toDTO(Contest contest) {
        ContestDTO dto = new ContestDTO();
        dto.setContestId(contest.getContestId());
        dto.setContestName(contest.getContestName());
        dto.setStartTime(contest.getStartTime());
        dto.setEndTime(contest.getStartTime().plusMinutes(contest.getDuration()));
        dto.setDuration(contest.getDuration());
        dto.setStatus(contest.getStatus());
        dto.setTotalCodingQuestions(contest.getTotalCodingQuestions());
        dto.setTotalQuantsMcqs(contest.getTotalQuantsMcqs());
        dto.setTotalTechnicalMcqs(contest.getTotalTechnicalMcqs());
        dto.setTotalVerbalMcqs(contest.getTotalVerbalMcqs());
        dto.setTotalReasoningMcqs(contest.getTotalReasoningMcqs());
        dto.setCategoryName(contest.getCategory().getCategoryName());
        return dto;
    }
}
