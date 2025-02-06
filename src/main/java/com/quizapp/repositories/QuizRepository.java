package com.quizapp.repositories;

import com.quizapp.constants.SqlScriptsFilePath;
import com.quizapp.ingestion.Quiz;
import com.quizapp.mappers.QuizRowMapper;
import com.quizapp.utils.ClassPathResourceReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
@Slf4j
public class QuizRepository {
    private final ClassPathResourceReader resourceReader;
    private final JdbcTemplate jdbcTemplate;

    public QuizRepository(ClassPathResourceReader resourceReader, JdbcTemplate jdbcTemplate) {
        this.resourceReader = resourceReader;
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertQuiz(Quiz quiz) {
        log.debug("Inserting quiz with quizId: {}", quiz.getQuizId());
        String sqlTemplate = this.resourceReader.readSqlFile(SqlScriptsFilePath.INSERT_QUIZ_SCRIPT_FILE_PATH);
        try {
            jdbcTemplate.update(sqlTemplate,
                    quiz.getQuizId(),
                    quiz.getQuizTitle(),
                    quiz.getCategoryId(),
                    quiz.getQuizDescription(),
                    quiz.getTimeLimit(),
                    quiz.getPassingScore(),
                    quiz.getCreatedBy(),
                    Timestamp.valueOf(quiz.getCreatedAt()));
        } catch (DataAccessException e){
            log.error("Error while inserting quiz with quiz id: {}", quiz.getQuizId(), e);
            throw e;
        }
    }

    public List<Quiz> getAll() {
        log.debug("Fetching all the quiz from database");
        String sqlTemplate = this.resourceReader.readSqlFile(SqlScriptsFilePath.SELECT_ALL_QUIZZES);
        return this.jdbcTemplate.query(sqlTemplate, new QuizRowMapper());
    }
}
