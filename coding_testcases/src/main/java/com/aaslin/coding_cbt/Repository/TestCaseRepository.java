package com.aaslin.coding_cbt.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.aaslin.coding_cbt.Entity.CodingTestCase;

public interface TestCaseRepository extends JpaRepository<CodingTestCase, String> {

    List<CodingTestCase> findByQuestion_Id(String questionId);

    @Query("SELECT MAX(tc.id) FROM CodingTestCase tc")
    String findMaxTestCaseId();

    void deleteByQuestionId(String questionId);
}
