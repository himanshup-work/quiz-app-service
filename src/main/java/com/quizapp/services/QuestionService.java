package com.quizapp.services;

import com.quizapp.ingestion.Question;

import java.util.List;

public interface QuestionService {
    void saveOrUpdateQuestions(List<Question> questions);
}
