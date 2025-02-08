package com.quizapp.services.impl;

import com.quizapp.exceptions.DatabaseException;
import com.quizapp.exceptions.InvalidInputException;
import com.quizapp.exceptions.ResourceNotFoundException;
import com.quizapp.ingestion.Option;
import com.quizapp.ingestion.Question;
import com.quizapp.ingestion.Quiz;
import com.quizapp.repositories.QuizRepository;
import com.quizapp.services.CategoryService;
import com.quizapp.services.OptionService;
import com.quizapp.services.QuestionService;
import com.quizapp.services.QuizService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {
    private final QuizRepository quizRepository;
    private final QuestionService questionService;
    private final OptionService optionService;
    private final CategoryService categoryService;

    @Override
    public Quiz createOrUpdateQuiz(Quiz quiz, String userId) {
        log.debug("Starting createOrUpdateQuiz operation for user: {}", userId);

        validateQuizInput(quiz, userId);

        try {
            // Handle category assignment
            if (StringUtils.hasText(quiz.getCategoryName())) {
                log.debug("Fetching category ID for category name: {}", quiz.getCategoryName());
                try {
                    int categoryId = this.categoryService.getCategoryIdByName(quiz.getCategoryName());
                    quiz.setCategoryId(categoryId);
                } catch (ResourceNotFoundException e) {
                    log.error("Category not found with name: {}", quiz.getCategoryName());
                    throw new InvalidInputException("Invalid category name provided: " + quiz.getCategoryName());
                }
            }

            // Generate new quiz ID if not present
            if (!StringUtils.hasText(quiz.getQuizId())) {
                val quizId = UUID.randomUUID().toString();
                quiz.setQuizId(quizId);
                quiz.setCreatedBy(userId);
                log.debug("Generated new quiz ID: {}", quizId);
            }

            quiz.setCreatedAt(LocalDateTime.now());
            List<Question> questions = new ArrayList<>();
            List<Option> options = new ArrayList<>();

            // Process questions and options
            processQuestionsAndOptions(quiz, questions, options);

            // Persist the data
            log.info("Saving quiz with ID: {}", quiz.getQuizId());
            this.quizRepository.insertQuiz(quiz);

            log.debug("Saving {} questions", questions.size());
            this.questionService.saveOrUpdateQuestions(questions);

            log.debug("Saving {} options", options.size());
            this.optionService.saveOrUpdateOptions(options);

            log.info("Successfully created/updated quiz with ID: {}", quiz.getQuizId());
            return quiz;

        } catch (DataAccessException e) {
            log.error("Database error while creating/updating quiz: {}", e.getMessage(), e);
            throw new DatabaseException("Failed to create/update quiz due to database error", e);
        } catch (Exception e) {
            log.error("Unexpected error while creating/updating quiz: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create/update quiz", e);
        }
    }

    @Override
    public List<Quiz> getAllQuizzes() {
        log.info("Fetching all quizzes from the database");
        try {
            List<Quiz> quizzes = this.quizRepository.getAll();
            log.debug("Found {} quizzes", quizzes.size());

            for (Quiz quiz : quizzes) {
                // Getting category name
                if (quiz.getCategoryId() != 0) {
                    log.debug("Fetching category name for category id: {}", quiz.getCategoryId());
                    String categoryId = this.categoryService.getCategoryNameByCategoryId(quiz.getCategoryId());
                    quiz.setCategoryName(categoryId);
                }
                log.debug("Fetching questions for quiz ID: {}", quiz.getQuizId());
                List<Question> questions = this.questionService.getAllQuestionsByQuizId(quiz.getQuizId());
                quiz.setQuestions(questions);
                log.debug("Added {} questions to quiz ID: {}", questions.size(), quiz.getQuizId());
            }

            log.info("Successfully retrieved {} quizzes with their questions", quizzes.size());
            return quizzes;

        } catch (EmptyResultDataAccessException e) {
            log.warn("No quizzes found in the database");
            throw new RuntimeException("No quizzes found");
        } catch (DataAccessException e) {
            log.error("Database error while fetching quizzes: {}", e.getMessage(), e);
            throw new DatabaseException("Failed to fetch quizzes due to database error", e);
        } catch (Exception e) {
            log.error("Unexpected error while fetching quizzes: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch quizzes", e);
        }
    }

    private void validateQuizInput(Quiz quiz, String userId) {
        if (quiz == null) {
            log.error("Quiz object is null");
            throw new InvalidInputException("Quiz cannot be null");
        }

        if (!StringUtils.hasText(userId)) {
            log.error("User ID is missing");
            throw new InvalidInputException("User ID is required");
        }

        if (quiz.getQuestions() == null || quiz.getQuestions().isEmpty()) {
            log.error("Quiz has no questions");
            throw new InvalidInputException("Quiz must contain at least one question");
        }

        log.debug("Quiz input validation passed for user: {}", userId);
    }

    private void processQuestionsAndOptions(Quiz quiz, List<Question> questions, List<Option> options) {
        log.debug("Processing questions and options for quiz ID: {}", quiz.getQuizId());

        for (Question question : quiz.getQuestions()) {
            if (!StringUtils.hasText(question.getQuestionText())) {
                log.error("Question text is missing in quiz ID: {}", quiz.getQuizId());
                throw new InvalidInputException("Question text cannot be empty");
            }

            question.setQuestionId(UUID.randomUUID().toString());
            question.setQuizId(quiz.getQuizId());
            questions.add(question);

            if (question.getOptions() == null || question.getOptions().isEmpty()) {
                log.error("No options provided for question ID: {}", question.getQuestionId());
                throw new InvalidInputException("Each question must have at least one option");
            }

            for (Option option : question.getOptions()) {
                if (!StringUtils.hasText(option.getOptionText())) {
                    log.error("Option text is missing in question ID: {}", question.getQuestionId());
                    throw new InvalidInputException("Option text cannot be empty");
                }

                option.setOptionId(UUID.randomUUID().toString());
                option.setQuestionId(question.getQuestionId());
                options.add(option);
            }
        }

        log.debug("Processed {} questions and {} options", questions.size(), options.size());
    }
}