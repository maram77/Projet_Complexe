package com.example.web_project.Service.product;


import com.example.web_project.Entity.Category;
import com.example.web_project.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        return categoryOptional.orElse(null);
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, Category newCategory) {
        Optional<Category> existingCategoryOptional = categoryRepository.findById(id);
        if (existingCategoryOptional.isPresent()) {
            Category existingCategory = existingCategoryOptional.get();
            existingCategory.setCategoryName(newCategory.getCategoryName());
            return categoryRepository.save(existingCategory);
        } else {
            return null;
        }
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    public List<Category> searchCategories(String keyword) {
        return categoryRepository.findByCategoryNameContainingIgnoreCase(keyword);
    }
}
