package com.aaslin.cbt.super_admin.repository;

import com.aaslin.cbt.common.model.MapContestMcq;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MapContestMcqRepository extends JpaRepository<MapContestMcq, String> {

    @Query("SELECT MAX(CAST(SUBSTRING(m.contestMcqMapId, 4) AS integer)) FROM MapContestMcq m")
    Integer findMaxMapNumber();
    List<MapContestMcq> findByContest_ContestId(String contestId);

    void deleteByContest_ContestId(String contestId);
}
 


