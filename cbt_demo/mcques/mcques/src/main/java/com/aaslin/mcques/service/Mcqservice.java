package com.aaslin.mcques.service;

import com.aaslin.mcques.model.Mcqmodel;
import com.aaslin.mcques.repo.Mcqrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Mcqservice {

    @Autowired
    private Mcqrepository mcqRepository;

    public List<Mcqmodel> getAllMCQs() {
        return mcqRepository.findAll();
    }

    public Optional<Mcqmodel> getMCQById(String mcqId) {
        return mcqRepository.findById(mcqId);
    }

    public Mcqmodel createMCQ(Mcqmodel mcq) {
        return mcqRepository.save(mcq);
    }

    public Optional<Mcqmodel> updateMCQ(String mcqId, Mcqmodel mcq) {
        return mcqRepository.findById(mcqId).map(existing -> {
            mcq.setMcqId(mcqId);
            return mcqRepository.save(mcq);
        });
    }

    public void deleteMCQ(String mcqId) {
        if (mcqRepository.existsById(mcqId)) {
            mcqRepository.deleteById(mcqId);
        }
    }
}