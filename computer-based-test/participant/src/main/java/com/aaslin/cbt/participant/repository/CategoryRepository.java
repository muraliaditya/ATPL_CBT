package com.aaslin.cbt.participant.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aaslin.cbt.common.model.Category;

@Repository("ParticipantCategoryRepository")
public interface CategoryRepository  extends JpaRepository<Category,String>{
	
	Optional<Category> findByCategoryName(String CategoryName);

}
