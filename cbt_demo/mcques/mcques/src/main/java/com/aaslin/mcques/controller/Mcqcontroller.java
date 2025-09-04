package com.aaslin.mcques.controller;

import com.aaslin.mcques.model.Mcqmodel;
import com.aaslin.mcques.service.Mcqservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mcqs")
public class Mcqcontroller {

    @Autowired
    private Mcqservice mcqService;

    @GetMapping
    public List<Mcqmodel> getAllMCQs() {
        return mcqService.getAllMCQs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mcqmodel> getMCQById(@PathVariable String id) {
        return ResponseEntity.of(mcqService.getMCQById(id));
    }

    @PostMapping("/")
    public ResponseEntity<Mcqmodel> createMCQ(@RequestBody Mcqmodel mcq) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mcqService.createMCQ(mcq));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mcqmodel> updateMCQ(@PathVariable String id, @RequestBody Mcqmodel mcq) {
        return ResponseEntity.of(mcqService.updateMCQ(id, mcq));
    }

    @DeleteMapping("/{id}")
    public void deleteMCQ(@PathVariable String id) {
        mcqService.deleteMCQ(id);
    }
}