package com.quizapp.repositories;

import com.quizapp.constants.SqlScriptsFilePath;
import com.quizapp.ingestion.Option;
import com.quizapp.mappers.OptionRowMapper;
import com.quizapp.utils.ClassPathResourceReader;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
@Slf4j
public class OptionRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ClassPathResourceReader resourceReader;

    public OptionRepository(@NonNull JdbcTemplate jdbcTemplate, @NonNull ClassPathResourceReader resourceReader) {
        this.jdbcTemplate = jdbcTemplate;
        this.resourceReader = resourceReader;
    }

    public void insertOptions(List<Option> options) {
        log.debug("Saving or updating {} options", options.size());
        String sqlTemplate = this.resourceReader.readSqlFile(SqlScriptsFilePath.INSERT_OPTION_SCRIPT_FILE_PATH);

        try {
            this.jdbcTemplate.batchUpdate(sqlTemplate,
                    new BatchPreparedStatementSetter() {

                        @Override
                        public void setValues(@NonNull PreparedStatement ps, int i) throws SQLException {
                            Option option = options.get(i);
                            ps.setString(1, option.getOptionId());
                            ps.setString(2, option.getOptionText());
                            ps.setString(3, option.getQuestionId());
                            ps.setBoolean(4, option.isCorrect());
                        }

                        @Override
                        public int getBatchSize() {
                            return options.size();
                        }
                    }
            );
        } catch (DataAccessException e) {
            log.error("Error saving or updating batch options", e);
            throw e;
        }
    }

    public List<Option> findOptionsByQuestionId(String questionId) {
        String sqlTemplate = this.resourceReader.readSqlFile(SqlScriptsFilePath.SELECT_ALL_OPTIONS_BY_QUESTION_ID);
        return this.jdbcTemplate.query(sqlTemplate, new OptionRowMapper(), questionId);
    }
}


