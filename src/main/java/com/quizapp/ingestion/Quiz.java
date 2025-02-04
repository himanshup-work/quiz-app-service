package com.quizapp.ingestion;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class Quiz {
    private String quizId;
    private String quizTitle;
    private String categoryName;
    @JsonIgnore
    private int categoryId;
    private String quizDescription;
    private Time timeLimit;
    private int passingScore;
    private String createdBy;
    private LocalDateTime createdAt;
    private List<Question> questions;
}
