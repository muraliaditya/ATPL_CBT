package com.aaslin.cbt.super_admin.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.aaslin.cbt.common.model.Category;

public interface CategoryRepository extends JpaRepository<Category, String> {
}
