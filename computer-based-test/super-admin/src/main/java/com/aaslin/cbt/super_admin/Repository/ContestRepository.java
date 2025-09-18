package com.aaslin.cbt.super_admin.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.aaslin.cbt.common.model.Contest;

public interface ContestRepository extends JpaRepository<Contest,String> {
	
	@Query("SELECT MAX(CAST(SUBSTRING(c.contestId, 4) AS int)) FROM Contest c")
	Integer findMaxContestNumber();
	List<Contest> findByDeletedFalse();


}
