package com.quizapp.repositories;

import com.quizapp.constants.SqlScriptsFilePath;
import com.quizapp.ingestion.Quiz;
import com.quizapp.utils.ClassPathResourceReader;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Repository
@Slf4j
public class QuizRepository {
    private static final String DATE_TIME_FORMAT = "yyyy-mm-dd hh:mm:ss";
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

    private Timestamp getTimestamp(@NonNull LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        System.out.println(localDateTime);
        try {
            String timestamp = localDateTime.format(formatter);
            Timestamp ts = new Timestamp(Long.parseLong(timestamp));
            System.out.println(ts);
            return Timestamp.valueOf(localDateTime.format(formatter));
        }catch (DateTimeException e){
            log.error("Error while parsing timestamp: {}", localDateTime, e);
            throw new DateTimeException("Error while parsing timestamp: " + localDateTime, e);
        }
    }

}
