package com.aaslin.cbt.super_admin.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.aaslin.cbt.common.model.Contest;

public interface ContestRepository extends JpaRepository<Contest, String>, JpaSpecificationExecutor<Contest> {

    @Query("SELECT MAX(CAST(SUBSTRING(c.contestId, 4) AS int)) FROM Contest c")
    Integer findMaxContestNumber();

    List<Contest> findByIsActiveTrue();

    Optional<Contest> findTopByOrderByContestIdDesc();

    boolean existsByContestName(String contestName);
}



    
