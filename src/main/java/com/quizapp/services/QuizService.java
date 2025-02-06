package com.quizapp.services;

import com.quizapp.ingestion.Quiz;

public interface QuizService {
    Quiz createOrUpdateQuiz(Quiz quiz, String userId);
}
