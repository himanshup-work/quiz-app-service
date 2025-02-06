package com.quizapp.services.impl;

import com.quizapp.exceptions.DatabaseException;
import com.quizapp.ingestion.Question;
import com.quizapp.repositories.QuestionRepository;
import com.quizapp.services.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public void saveOrUpdateQuestions(List<Question> questions) {
        log.info("Saving or updating {} questions", questions.size());

        // Assign UUIDs if not already set
        for (Question question : questions) {
            if (question.getQuestionId() == null || question.getQuestionId().isEmpty()) {
                question.setQuestionId(UUID.randomUUID().toString());
            }
        }

        try {
            this.questionRepository.insertQuestions(questions);
            log.info("Questions saved or updated successfully");
        } catch (DataAccessException e) {
            log.error("Database error while saving or updating questions", e);
            throw new DatabaseException("Database error while saving or updating questions: " + e.getMessage());
        }
    }
}

