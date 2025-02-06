package com.quizapp.services;

import com.quizapp.ingestion.Quiz;

import java.util.List;

public interface QuizService {
    Quiz createOrUpdateQuiz(Quiz quiz, String userId);

    List<Quiz> getAllQuizzes();
}
