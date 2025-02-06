package com.quizapp.mappers;

import com.quizapp.ingestion.Question;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QuestionRowMapper implements RowMapper<Question> {
    @Override
    public Question mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Question.builder()
                .questionId(rs.getString("question_id"))
                .questionText(rs.getString("question_text"))
                .quizId(rs.getString("quiz_id"))
                .build();
    }
}
