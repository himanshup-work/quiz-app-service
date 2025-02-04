package com.quizapp.repositories;

import com.quizapp.utils.ClassPathResourceReader;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class CategoryRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ClassPathResourceReader resourceReader;

    public CategoryRepository(@NonNull JdbcTemplate jdbcTemplate, @NonNull ClassPathResourceReader resourceReader) {
        this.jdbcTemplate = jdbcTemplate;
        this.resourceReader = resourceReader;
    }

    public int getCategoryId(@NonNull String categoryName) {
        log.info("Fetching category id for category name: {}", categoryName);
        String sql = "SELECT category_id FROM category WHERE LOWER(name) = LOWER(?)";

        try {
            Integer categoryId = jdbcTemplate.queryForObject(sql, Integer.class, categoryName);
            if (categoryId != null) {
                return categoryId;
            }
        } catch (DataAccessException e) {
            log.error("Error while fetching category id for name: {}", categoryName, e);
            throw e;
        }
        return 0;
    }

}
