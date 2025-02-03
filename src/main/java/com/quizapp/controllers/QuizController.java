package com.quizapp.controllers;

import com.quizapp.ingestion.Quiz;
import com.quizapp.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quiz")
public class QuizController {
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createQuiz(@RequestBody Quiz quiz) {

    }
}
