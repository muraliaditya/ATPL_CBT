package com.aaslin.mcques.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aaslin.mcques.model.Contestmodel;

public interface ContestRepository extends JpaRepository<Contestmodel, String>
{

}
