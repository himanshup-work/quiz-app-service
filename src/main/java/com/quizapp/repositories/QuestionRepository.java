package com.quizapp.repositories;

import com.quizapp.constants.SqlScriptsFilePath;
import com.quizapp.ingestion.Question;
import com.quizapp.utils.ClassPathResourceReader;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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

    public void saveOrUpdateQuestions(List<Question> questions) {
        log.debug("Saving or updating {} questions", questions.size());
        String sqlTemplate = this.resourceReader.readSqlFile(SqlScriptsFilePath.INSERT_QUESTION_SCRIPT_FILE_PATH);

        try {
            this.jdbcTemplate.batchUpdate(sqlTemplate,
                    questions,
                    questions.size(), // Batch size (adjust if needed)
                    (ps, question) -> {
                        ps.setString(1, question.getQuestionId());
                        ps.setString(2, question.getQuizId());
                        ps.setString(3, question.getQuestionText());
                    }
            );
        } catch (DataAccessException e) {
            log.error("Error saving or updating batch questions", e);
            throw e;
        }
    }
}


