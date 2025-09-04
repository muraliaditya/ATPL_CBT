package com.aaslin.testcase.testcases.repository;
import com.aaslin.testcase.testcases.model.Testcase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestcaseRepository extends JpaRepository<Testcase, String> {
}