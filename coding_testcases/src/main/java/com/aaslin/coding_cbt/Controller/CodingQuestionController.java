package com.aaslin.coding_cbt.Controller;

import org.springframework.web.bind.annotation.*;

import com.aaslin.coding_cbt.Entity.CodingQuestion;
import com.aaslin.coding_cbt.Entity.CodingTestCase;
import com.aaslin.coding_cbt.Service.CodingQuestionService;
import com.aaslin.coding_cbt.Service.TestCaseService;

import java.util.List;

@RestController
@RequestMapping("/api/coding-questions")
public class CodingQuestionController {

    private final CodingQuestionService service;
    private final TestCaseService testcaseService;

    
    public CodingQuestionController(CodingQuestionService service, TestCaseService testcaseService) {
		super();
		this.service = service;
		this.testcaseService = testcaseService;
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
    
    @GetMapping("/{id}/testcases")
    public List<CodingTestCase> getTestCasesByQuestion(@PathVariable String id){
    	return testcaseService.getTestcasesByQuestionId(id);
    }
}
