package com.aaslin.cbtdemo.cbt_demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.aaslin.cbtdemo.cbt_demo.model.Mcq_questions;
import com.aaslin.cbtdemo.cbt_demo.repository.Studentrep;

@RestController
public class Mcqcontroller {
	
	@Autowired
	Studentrep repo;
	
	@GetMapping("/mcques")
	public List<Mcq_questions> getallques(){
			List<Mcq_questions>questions=repo.findAll();
			return questions;
		}
	@GetMapping("/mcques/{mcq_id}")
	public Mcq_questions getques(@PathVariable int mcq_id) {
		Mcq_questions mcques=repo.findById(mcq_id).get();
		return mcques;
	}
	
	@PostMapping("/mcques/add")
	public void addques(@RequestBody Mcq_questions mcq_qb) {
		repo.save(mcq_qb);
	}
	
	public void updateques() {}
	@PutMapping("/mcques/{mcq_id}")
    public ResponseEntity<Mcq_questions> updateQues(@PathVariable Integer mcq_id, @RequestBody Mcq_questions mcq_qu) {
		Mcq_questions mcq_qes = repo.findById(mcq_id).orElse(null);
        if (mcq_qes == null) {
            return ResponseEntity.notFound().build();
        }

        mcq_qes.setQuestion(mcq_qu.getQuestion());
        mcq_qes.setAnswer(mcq_qu.getAnswer());
        mcq_qes.setOption1(mcq_qu.getOption1());
        mcq_qes.setOption2(mcq_qu.getOption2());
        mcq_qes.setOption3(mcq_qu.getOption3());
        mcq_qes.setOption4(mcq_qu.getOption4());

        Mcq_questions updatedUser = repo.save(mcq_qes);

        return ResponseEntity.ok(updatedUser);
    }
}
