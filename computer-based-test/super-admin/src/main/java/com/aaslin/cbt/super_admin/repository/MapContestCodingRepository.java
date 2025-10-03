package com.aaslin.cbt.super_admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.aaslin.cbt.common.model.CodingQuestion;
import com.aaslin.cbt.common.model.Contest;
import com.aaslin.cbt.common.model.MapContestCoding;

@Repository
public interface MapContestCodingRepository extends JpaRepository<MapContestCoding, String> {
    List<MapContestCoding> findByContest(Contest contest);
    List<MapContestCoding> findByCodingQuestion(CodingQuestion codingQuestion);
    void deleteByContestAndCodingQuestion(Contest contest, CodingQuestion codingQuestion);
    
    List<MapContestCoding> findByContest_ContestId(String contestId);

    void deleteByContest_ContestId(String contestId);

    @Query("SELECT MAX(CAST(SUBSTRING(m.contestCodingMapId, 4) AS int)) FROM MapContestCoding m")
    Integer findMaxMapNumber();
}
