package com.quizapp.repositories;

import com.quizapp.constants.SqlScriptsFilePath;
import com.quizapp.ingestion.Question;
import com.quizapp.mappers.QuestionRowMapper;
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
public class QuestionRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ClassPathResourceReader resourceReader;

    public QuestionRepository(@NonNull JdbcTemplate jdbcTemplate, @NonNull ClassPathResourceReader resourceReader) {
        this.jdbcTemplate = jdbcTemplate;
        this.resourceReader = resourceReader;
    }

    public void insertQuestions(List<Question> questions) {
        log.debug("Saving or updating {} questions", questions.size());
        String sqlTemplate = this.resourceReader.readSqlFile(SqlScriptsFilePath.INSERT_QUESTION_SCRIPT_FILE_PATH);

        try {
            this.jdbcTemplate.batchUpdate(sqlTemplate,
                    new BatchPreparedStatementSetter() {
                        @Override
                        public void setValues(@NonNull PreparedStatement ps, int i) throws SQLException {
                            Question question = questions.get(i);
                            ps.setString(1, question.getQuestionId());
                            ps.setString(2, question.getQuestionText());
                            ps.setString(3, question.getQuizId());
                        }

                        @Override
                        public int getBatchSize() {
                            return questions.size();
                        }
                    }
            );
        } catch (DataAccessException e) {
            log.error("Error saving or updating batch questions", e);
            throw e;
        }
    }

    public List<Question> findQuestionsByQuizId(String quizId) {
        String sqlTemplate = this.resourceReader.readSqlFile(SqlScriptsFilePath.SELECT_ALL_QUESTIONS_BY_QUIZ_ID);
        return this.jdbcTemplate.query(sqlTemplate, new QuestionRowMapper(), quizId);

    }
}


