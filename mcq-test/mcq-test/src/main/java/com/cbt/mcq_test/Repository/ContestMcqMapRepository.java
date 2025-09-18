package com.cbt.mcq_test.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cbt.mcq_test.entity.ContestMcqMap;

@Repository
public interface ContestMcqMapRepository extends JpaRepository<ContestMcqMap, String> {
    List<ContestMcqMap> findByContest_ContestId(String contestId);
    
    List<ContestMcqMap> findBySection(String section);
}

