package com.quizapp.ingestion;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
public class Quiz {
    private String quizId;
    private String quizTitle;
    private String categoryId;
    private String quizDescription;
    private String timeLimit;
    private String passingScore;
    private String createdBy;
    private Timestamp createdAt;
    private List<Question> questions;
}
