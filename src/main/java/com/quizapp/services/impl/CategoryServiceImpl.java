package com.quizapp.services.impl;

import com.quizapp.exceptions.DatabaseException;
import com.quizapp.exceptions.ResourceNotFoundException;
import com.quizapp.repositories.CategoryRepository;
import com.quizapp.services.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public int getCategoryIdByName(String categoryName) {
        try {
            int categoryId = this.categoryRepository.getCategoryId(categoryName);
            if (categoryId == 0) {
                log.error("Category Id not found with category name: {}", categoryName);
                throw new ResourceNotFoundException("Category Id", "category name", categoryName);
            }
            return categoryId;
        } catch (DataAccessException e) {
            log.error("Database error while finding Category Id by category name: {}", categoryName, e);
            throw new DatabaseException("Database error while finding Category Id by category name: " + e.getMessage());
        }
    }
}
