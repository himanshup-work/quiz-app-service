package com.quizapp.mappers;

import com.quizapp.ingestion.Option;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OptionRowMapper implements RowMapper<Option> {
    @Override
    public Option mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Option.builder()
                .optionId(rs.getString("option_id"))
                .optionText(rs.getString("option_text"))
                .questionId(rs.getString("question_id"))
                .isCorrect(rs.getBoolean("is_correct"))
                .build();
    }
}
