package com.quizapp.services.impl;

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
import org.springframework.stereotype.Service;

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
        if (quiz.getCategoryName() != null) {
            quiz.setCategoryId(
                    this.categoryService.getCategoryIdByName(quiz.getCategoryName()));
        }
        if (quiz.getQuizId() == null || quiz.getQuizId().isEmpty()) {
            val quizId = UUID.randomUUID().toString();
            quiz.setQuizId(quizId);
            quiz.setCreatedBy(userId);
        }
        quiz.setCreatedAt(LocalDateTime.now());
        List<Question> questions = new ArrayList<>();
        List<Option> options = new ArrayList<>();

        for (Question question : quiz.getQuestions()) {
            question.setQuestionId(UUID.randomUUID().toString());
            question.setQuizId(quiz.getQuizId());
            questions.add(question);

            for (Option option : question.getOptions()) {
                option.setOptionId(UUID.randomUUID().toString());
                option.setQuestionId(question.getQuestionId());
                options.add(option);
            }
        }
        this.quizRepository.insertQuiz(quiz);
        this.questionService.saveOrUpdateQuestions(questions);
        this.optionService.saveOrUpdateOptions(options);
        return null;
    }

    @Override
    public List<Quiz> getAllQuizzes() {
        List<Quiz> quizzes = this.quizRepository.getAll();
        for (Quiz quiz : quizzes){
            List<Question> questions = this.questionService.getAllQuestionsByQuizId(quiz.getQuizId());
            quiz.setQuestions(questions);
        }
        return quizzes;
    }
}
