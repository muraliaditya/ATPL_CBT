package com.aaslin.cbt.super_admin.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.aaslin.cbt.common.model.Contest;
import com.aaslin.cbt.common.model.Submission;
import com.aaslin.cbt.super_admin.dto.ContestResultDTO;
import com.aaslin.cbt.super_admin.dto.ContestResultsResponse;
import com.aaslin.cbt.super_admin.exceptions.CustomExceptions.ContestNotFoundException;
import com.aaslin.cbt.super_admin.repository.ContestRepository;
import com.aaslin.cbt.super_admin.repository.SubmissionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContestResultService {

    private final SubmissionRepository submissionRepo;
    private final ContestRepository contestRepo;

    public ContestResultsResponse getContestResults(String contestId) {
        Contest contest = contestRepo.findById(contestId)
                .orElseThrow(() -> new ContestNotFoundException(contestId));

        String eligibility = contest.getCategory().getCategoryName();
        List<Submission> submissions = submissionRepo.findByContestContestId(contestId);

        List<ContestResultDTO> results = submissions.stream().map(sub -> {
            ContestResultDTO dto = new ContestResultDTO();
            dto.setSubmissionId(sub.getSubmissionId());
            dto.setParticipantId(sub.getParticipant().getParticipantId());
            dto.setUserName(sub.getParticipant().getName());
            dto.setEmail(sub.getParticipant().getEmail());
            dto.setCodingMarks(sub.getTotalCodingScore());
            dto.setMcqMarks(sub.getTotalMcqScore());
            dto.setTotalMarks(sub.getTotalScore());

            if ("Student".equalsIgnoreCase(eligibility)) {
                dto.setCollege(sub.getParticipant().getCollege() != null ? sub.getParticipant().getCollege().getCollegeName() : "");
                dto.setCollegeRegdNo(sub.getParticipant().getCollegeRegdNo());
                if (sub.getParticipant().getPercentage() != null) {
                    dto.setPercentage(sub.getParticipant().getPercentage().doubleValue());
                }
            } else if ("Experienced".equalsIgnoreCase(eligibility)) {
                dto.setCompany(sub.getParticipant().getCompany() != null ? sub.getParticipant().getCompany().getCurrentCompanyName() : "");
                dto.setDesignation(sub.getParticipant().getDesignation() != null ? sub.getParticipant().getDesignation().getDesignationName() : "");
                dto.setOverallExperience(sub.getParticipant().getOverallExperience());
            }
            return dto;
        }).collect(Collectors.toList());

        return ContestResultsResponse.builder()
                .eligibility(eligibility)
                .results(results)
                .build();
    }

    public ByteArrayInputStream generateExcel(String contestId) {
        ContestResultsResponse response = getContestResults(contestId);
        List<ContestResultDTO> results = response.getResults();

        try (Workbook workbook = new XSSFWorkbook();
        	ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Contest Results");

            Row headerRow = sheet.createRow(0);
            String[] headers = {
                "Submission ID", "Participant ID", "Username", "Email", "College", "College Regd No",
                "Percentage", "Coding Marks", "MCQ Marks", "Total Marks"
            };
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }           
            int rowIdx = 1;
            for (ContestResultDTO dto : results) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(dto.getSubmissionId() != null ? dto.getSubmissionId() : "");
                row.createCell(1).setCellValue(dto.getParticipantId() != null ? dto.getParticipantId() : "");
                row.createCell(2).setCellValue(dto.getUserName() != null ? dto.getUserName() : "");
                row.createCell(3).setCellValue(dto.getEmail() != null ? dto.getEmail() : "");
                row.createCell(4).setCellValue(dto.getCollege() != null ? dto.getCollege() : "");
                row.createCell(5).setCellValue(dto.getCollegeRegdNo() != null ? dto.getCollegeRegdNo() : "");
                row.createCell(6).setCellValue(dto.getPercentage() != null ? dto.getPercentage() : 0);
                row.createCell(7).setCellValue(dto.getCodingMarks() != null ? dto.getCodingMarks() : 0);
                row.createCell(8).setCellValue(dto.getMcqMarks() != null ? dto.getMcqMarks() : 0);
                row.createCell(9).setCellValue(dto.getTotalMarks() != null ? dto.getTotalMarks() : 0);
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Failed to generate Excel file", e);
        }
    }
}
