package com.example.cbt_system.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.example.cbt_system.entity.Question;
import com.example.cbt_system.repository.QuestionRepository;

@Service
public class CsvImportService {

    private final QuestionRepository questionRepo;

    public CsvImportService(QuestionRepository questionRepo) {
        this.questionRepo = questionRepo;
    }

    public List<Question> importQuestions(MultipartFile file) {
        List<Question> questions = new ArrayList<>();

        try (
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
            @SuppressWarnings("deprecation")
			CSVParser csvParser = new CSVParser(reader,
                    CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())
        ) {
            for (CSVRecord record : csvParser) {
                Question q = new Question();
                q.setTitle(record.get("title"));
                q.setDescription(record.get("description"));
                q.setLevel(record.get("difficulty"));
                q.setInputFormat(record.get("inputFormat"));
                q.setOutputFormat(record.get("outputFormat"));
                q.setSampleInput(record.get("sampleInput"));
                q.setSampleOutput(record.get("sampleOutput"));
                q.setCreatedBy(record.get("createdBy"));

                // Default status = PENDING (super admin can later approve)
                q.setStatus("pending");

                questions.add(q);
            }
            questionRepo.saveAll(questions);

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse CSV file: " + e.getMessage());
        }
        return questions;
    }
}