package com.quizapp.mappers;

import com.quizapp.ingestion.Category;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryRowMapper
        implements RowMapper<Category> {
    @Override
    public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Category.builder()
                .categoryId(rs.getInt("category_id"))
                .categoryName(rs.getString("category_name"))
                .description(rs.getString("description"))
                .build();
    }
}
