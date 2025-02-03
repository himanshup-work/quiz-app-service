package com.quizapp.ingestion;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Question {
    private String questionId;
    private String questionText;
    private String quizId;
    private List<Option> options;
}
