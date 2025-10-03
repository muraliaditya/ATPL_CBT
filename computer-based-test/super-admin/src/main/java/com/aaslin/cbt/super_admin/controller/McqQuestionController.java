package com.aaslin.cbt.super_admin.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aaslin.cbt.super_admin.dto.ApiResponse;
import com.aaslin.cbt.super_admin.dto.McqQuestionDTO;
import com.aaslin.cbt.super_admin.service.McqQuestionService;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/mcqs")
@RequiredArgsConstructor
public class McqQuestionController {

	@Autowired
    private final McqQuestionService mcqService;
    
	@GetMapping
    public ResponseEntity<List<McqQuestionDTO>> getBySection(@RequestParam String sectionName) {
        List<McqQuestionDTO> questions = mcqService.getQuestionsBySection(sectionName);
        return ResponseEntity.ok(questions);
    }
    @PostMapping
    public ResponseEntity<ApiResponse> addMultipleMcqs(@RequestBody List<McqQuestionDTO> dtos) {
        mcqService.addQuestions(dtos);
        return ResponseEntity.ok(new ApiResponse("Success","MCQs are Added Succesfully"));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateMcq(@PathVariable String id, @RequestBody McqQuestionDTO dto) {
        mcqService.updateMcq(id, dto);
        return ResponseEntity.ok(new ApiResponse("Success","MCQS are Updated Succesfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteMcq(@PathVariable String id) {
        mcqService.safeDelete(id);
        return ResponseEntity.ok(new ApiResponse("Success","MCQ deleted Succesfullly"));
    }  
    
    @GetMapping("/random")
        public ResponseEntity<List<McqQuestionDTO>> getRandomQuestions(
                @RequestParam String sectionName,
                @RequestParam int count) {
            return ResponseEntity.ok(mcqService.generateRandomQuestions(sectionName, count));
        }
    
    @GetMapping("/regenerate")
        public ResponseEntity<McqQuestionDTO> regenerateQuestion(
                @RequestParam String sectionName,
                @RequestParam String currentQuestionId) {
            return ResponseEntity.ok(mcqService.regenerateQuestion(sectionName, currentQuestionId));
        }
}