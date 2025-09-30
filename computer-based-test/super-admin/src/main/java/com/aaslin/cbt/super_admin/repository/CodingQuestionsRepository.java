package com.aaslin.cbt.super_admin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.aaslin.cbt.common.model.CodingQuestions;

@Repository
public interface CodingQuestionsRepository extends JpaRepository<CodingQuestions, String> {

    @Query("SELECT c FROM CodingQuestions c " +
           "WHERE c.isActive = true AND LOWER(c.question) LIKE LOWER(CONCAT('%', :question, '%'))")
    Page<CodingQuestions> searchByQuestion(@Param("question") String question, Pageable pageable);

    @Query(value = "SELECT * FROM coding_questions_cbt c " +
                   "WHERE c.is_active = true AND c.difficulty = :difficulty " +
                   "ORDER BY RAND() LIMIT 1", nativeQuery = true)
    CodingQuestions findRandomByDifficulty(@Param("difficulty") String difficulty);

    @Query(value = "SELECT * FROM coding_questions_cbt c " +
                   "WHERE c.is_active = true AND c.difficulty = :difficulty " +
                   "AND c.coding_question_id <> :questionId " +
                   "ORDER BY RAND() LIMIT 1", nativeQuery = true)
    CodingQuestions findRandomByDifficultyExcluding(@Param("difficulty") String difficulty,
                                                    @Param("questionId") String questionId);

    @Query(value = "SELECT * FROM coding_questions_cbt c " +
                   "WHERE c.is_active = true AND c.difficulty = :difficulty " +
                   "ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    List<CodingQuestions> findNRandomByDifficulty(@Param("difficulty") String difficulty,
                                                  @Param("limit") int limit);

    Optional<CodingQuestions> findTopByOrderByCodingQuestionIdDesc();

    @Query(value = "SELECT * FROM coding_questions_cbt c " +
                   "WHERE c.is_active = true ORDER BY RAND() LIMIT :count", nativeQuery = true)
    List<CodingQuestions> findRandom(@Param("count") int count);

    List<CodingQuestions> findByIsActiveFalse();   

}