package com.aaslin.cbt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aaslin.cbt.entity.Contest;

@Repository
public interface ContestRepository extends JpaRepository<Contest, String> {

    @Query("SELECT c.contestId FROM Contest c")
    List<String> findAllContestIds();

    List<Contest> findByContestId(String contestId);
}

