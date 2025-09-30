package com.aaslin.cbt.super_admin.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.aaslin.cbt.common.model.Category;

public interface CategoryRepository extends JpaRepository<Category, String> {
	Optional<Category> findByCategoryName(String categoryName);
}
