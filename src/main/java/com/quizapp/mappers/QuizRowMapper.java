package com.quizapp.mappers;

import com.quizapp.ingestion.Quiz;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QuizRowMapper implements RowMapper<Quiz> {
    @Override
    public Quiz mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Quiz.builder()
                .quizId(rs.getString("quiz_id"))
                .quizTitle(rs.getString("quiz_title"))
                .categoryId(rs.getInt("category_id"))
                .quizDescription(rs.getString("quiz_description"))
                .timeLimit(rs.getTime("time_limit"))
                .passingScore(rs.getInt("passing_score"))
                .createdBy(rs.getString("created_by"))
                .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                .build();
    }
}
