package com.quizapp.services;

import com.quizapp.ingestion.Option;

import java.util.List;

public interface OptionService {
    void saveOrUpdateOptions(List<Option> options);

    List<Option> getAllOptionsByQuestionId(String questionId);
}
