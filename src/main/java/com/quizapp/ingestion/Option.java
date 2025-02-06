package com.quizapp.ingestion;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Option {
    private String optionId;
    private String optionText;
    private String questionId;
    private boolean isCorrect;
}
