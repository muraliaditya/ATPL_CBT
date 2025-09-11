package com.aaslin.coding_cbt.Controller;

import org.springframework.web.bind.annotation.*;

import com.aaslin.coding_cbt.Entity.CodingQuestion;
import com.aaslin.coding_cbt.Service.CodingQuestionService;

import java.util.List;

@RestController
@RequestMapping("/api/coding-questions")
public class CodingQuestionController {

    private final CodingQuestionService service;

    public CodingQuestionController(CodingQuestionService service) {
        this.service = service;
    }

    @PostMapping
    public CodingQuestion create(@RequestBody CodingQuestion cq) {
        return service.createQuestion(cq);
    }

    @PutMapping("/{id}")
    public CodingQuestion update(@PathVariable String id, @RequestBody CodingQuestion cq) {
        return service.updateQuestion(id, cq);
    }

    @GetMapping
    public List<CodingQuestion> getAll() {
        return service.getAllQuestions();
    }

    @GetMapping("/{id}")
    public CodingQuestion getById(@PathVariable String id) {
        return service.getQuestionById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.deleteQuestion(id);
    }
}
