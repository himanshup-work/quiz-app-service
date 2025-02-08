package com.quizapp.services;

public interface CategoryService {
    int getCategoryIdByName(String categoryName);
    String getCategoryNameByCategoryId(int categoryId);
}
