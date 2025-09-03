package com.aaslin.cbt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.aaslin.cbt.entity.MCQQuestion;
import com.aaslin.cbt.service.MCQQuestionService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MCQQuestionController {
    
	@Autowired
	private MCQQuestionService mcqservice;

    @GetMapping("/api/admin/mcqs/{}")
    public List<MCQQuestion> findAll(@PathVariable String id) {
        return mcqservice.getByContestId(id);
    }

    @PostMapping("/test{id}/mcqcreate")
    public MCQQuestion create(@RequestBody MCQQuestion q,@PathVariable Long id) {
        return mcqservice.create(q);
    }
    
    @PutMapping("/test{id}/mcqedit")
    public MCQQuestion edit(@RequestBody MCQQuestion q,@PathVariable Long id) {
		return mcqservice.Edit(id, q);   	
    }
    @DeleteMapping("/test{id}/delete")
    public void delete(@PathVariable Long id) {
    	mcqservice.delete(id);
    }
      
}