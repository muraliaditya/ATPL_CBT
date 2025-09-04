package com.aaslin.mcques.repo;

import com.aaslin.mcques.model.Mcqmodel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Mcqrepository extends JpaRepository<Mcqmodel, String> {
}