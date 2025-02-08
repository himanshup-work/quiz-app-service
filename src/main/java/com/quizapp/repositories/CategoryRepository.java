package com.quizapp.repositories;

import com.quizapp.exceptions.ResourceNotFoundException;
import com.quizapp.ingestion.Category;
import com.quizapp.mappers.CategoryRowMapper;
import com.quizapp.utils.ClassPathResourceReader;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class CategoryRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ClassPathResourceReader resourceReader;

    public CategoryRepository(@NonNull JdbcTemplate jdbcTemplate, @NonNull ClassPathResourceReader resourceReader) {
        this.jdbcTemplate = jdbcTemplate;
        this.resourceReader = resourceReader;
    }

    public List<Category> findAll() {
        log.info("Fetching all categories");
        String sql = "SELECT * FROM category";
        try {
            List<Category> categories = this.jdbcTemplate.query(sql, new CategoryRowMapper());
            if (categories.isEmpty()) {
                log.info("No categories found");
                throw new ResourceNotFoundException("No Categories found");
            }
            return categories;
        }catch (DataAccessException e) {
            log.error(e.getMessage());
            throw e;
        }

    }

    public int getCategoryId(@NonNull String categoryName) {
        log.info("Fetching category id for category name: {}", categoryName);
        String sql = "SELECT category_id FROM category WHERE LOWER(name) = LOWER(?)";

        try {
            Integer categoryId = jdbcTemplate.queryForObject(sql, Integer.class, categoryName);
            if (categoryId == null) {
                log.info("No category found for category name: {}", categoryName);
                throw new ResourceNotFoundException("No category found for category name: " + categoryName);
            }
            return categoryId;
        } catch (DataAccessException e) {
            log.error("Error while fetching category id for name: {}", categoryName, e);
            throw e;
        }
    }

    public String getCategoryNameById(int categoryId) {
        log.info("Fetching category name for category id: {}", categoryId);
        String sql = "SELECT category_name FROM category WHERE category_id = ?";
        try {
            String categoryName = jdbcTemplate.queryForObject(sql, String.class, categoryId);
            if (!StringUtils.hasText(categoryName)) {
                log.info("No category found for category id: {}", categoryId);
                throw new ResourceNotFoundException("No category found for category id: " + categoryId);
            }
            return categoryName;
        } catch (DataAccessException e) {
            log.error("Error while fetching category name for id: {}", categoryId, e);
            throw e;
        }
    }
}
