package com.aaslin.cbt.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.aaslin.cbt.entity.CodingQuestion;
//import com.aaslin.cbt.service.CodingQuestionService;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api")
//public class CodingQuestionController {
//  
//	@Autowired
//	private CodingQuestionService codingservice;
//    
//
//    @GetMapping("/test{id}/coding")
//    public List<CodingQuestion> findAll(@PathVariable Long id) {
//        return codingservice.getall();
//        }
//
//    @PostMapping("/test{id}/codingcreate")
//    public CodingQuestion create(@RequestBody CodingQuestion q,@PathVariable Long id) {
//        return codingservice.create(q);
//    }
//    @PutMapping("/test{id}/mcqedit")
//    public CodingQuestion edit(@RequestBody CodingQuestion q,@PathVariable Long id) {
//    	return codingservice.Edit(id, q);    
//    }
//    @DeleteMapping("/test{id}/delete")
//    public void delete(@PathVariable Long id) {
//    	codingservice.delete(id);
//    }
//
//}