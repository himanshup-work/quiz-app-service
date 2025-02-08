package com.quizapp.services.impl;

import com.quizapp.exceptions.DatabaseException;
import com.quizapp.exceptions.InvalidInputException;
import com.quizapp.exceptions.ResourceNotFoundException;
import com.quizapp.repositories.CategoryRepository;
import com.quizapp.services.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public int getCategoryIdByName(String categoryName) {
        log.debug("Attempting to find category ID for category name: {}", categoryName);

        if (!StringUtils.hasText(categoryName)) {
            log.error("Category name is null or empty");
            throw new InvalidInputException("Category name cannot be null or empty");
        }

        try {
            int categoryId = this.categoryRepository.getCategoryId(categoryName);
            if (categoryId == 0) {
                log.error("Category not found with name: {}", categoryName);
                throw new ResourceNotFoundException("Category", "name", categoryName);
            }

            log.debug("Successfully found category ID: {} for category name: {}", categoryId, categoryName);
            return categoryId;

        } catch (DataAccessException e) {
            log.error("Database error while finding category ID for name: {}", categoryName, e);
            throw new DatabaseException("Failed to retrieve category ID due to database error", e);
        } catch (Exception e) {
            log.error("Unexpected error while finding category ID for name: {}", categoryName, e);
            throw new RuntimeException("Failed to retrieve category ID", e);
        }
    }

    @Override
    public String getCategoryNameByCategoryId(int categoryId) {
        log.debug("Attempting to find category name for category ID: {}", categoryId);

        if (categoryId == 0) {
            log.error("Category ID is null or empty");
            throw new InvalidInputException("Category ID cannot be null or empty");
        }

        try {
            String categoryName = this.categoryRepository.getCategoryNameById(categoryId);
            if (!StringUtils.hasText(categoryName)) {
                log.error("Category not found with ID: {}", categoryId);
                throw new ResourceNotFoundException("Category", "ID", String.valueOf(categoryId));
            }

            log.debug("Successfully found category name: {} for category ID: {}", categoryName, categoryId);
            return categoryName;

        } catch (DataAccessException e) {
            log.error("Database error while finding category name for ID: {}", categoryId, e);
            throw new DatabaseException("Failed to retrieve category name due to database error", e);
        } catch (Exception e) {
            log.error("Unexpected error while finding category name for ID: {}", categoryId, e);
            throw new RuntimeException("Failed to retrieve category name", e);
        }
    }
}