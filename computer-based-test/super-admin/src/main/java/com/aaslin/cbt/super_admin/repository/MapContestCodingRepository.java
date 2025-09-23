package com.aaslin.cbt.super_admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aaslin.cbt.common.model.CodingQuestions;
import com.aaslin.cbt.common.model.Contest;
import com.aaslin.cbt.common.model.MapContestCoding;

@Repository
public interface MapContestCodingRepository extends JpaRepository<MapContestCoding, String> {
    List<MapContestCoding> findByContest(Contest contest);
    List<MapContestCoding> findByCodingQuestion(CodingQuestions codingQuestion);
    void deleteByContestAndCodingQuestion(Contest contest, CodingQuestions codingQuestion);
}
