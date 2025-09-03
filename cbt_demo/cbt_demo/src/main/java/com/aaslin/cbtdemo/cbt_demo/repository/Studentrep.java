package com.aaslin.cbtdemo.cbt_demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aaslin.cbtdemo.cbt_demo.model.Mcq_questions;

public interface Studentrep extends JpaRepository<Mcq_questions, Integer>{

}
