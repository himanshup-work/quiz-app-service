package com.quizapp.ingestion;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Category {
    private int categoryId;
    private String categoryName;
    private String description;
}
