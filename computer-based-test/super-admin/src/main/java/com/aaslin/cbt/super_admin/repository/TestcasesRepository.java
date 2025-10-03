package com.aaslin.cbt.super_admin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aaslin.cbt.common.model.Testcase;

@Repository
public interface TestcasesRepository extends JpaRepository<Testcase, String> {
	Optional<Testcase> findTopByOrderByTestcaseIdDesc();

}