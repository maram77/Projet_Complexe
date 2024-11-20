package com.example.web_project.Repository;

import com.example.web_project.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByCategoryNameContainingIgnoreCase(String keyword);
}
