package com.quizapp.controllers;

import com.quizapp.ingestion.Quiz;
import com.quizapp.services.QuizService;
import com.quizapp.utils.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/quiz")
public class QuizController {
    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }


    // Controller for add new quiz
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createQuiz(@RequestBody Quiz quiz) {
        // Get logged-in user using SecurityContextHolder
        String loggedInUser = getCurrentUsername();
        System.out.println(loggedInUser);

        // Validate user is authenticated
        if (loggedInUser == null) {
            return new ResponseEntity<>(
                    ApiResponse.builder()
                            .message("User not authenticated")
                            .status(false)
                            .build(),
                    HttpStatus.UNAUTHORIZED
            );
        }

        // Save Quiz
        Quiz createdQuiz = this.quizService.createOrUpdateQuiz(quiz, "user1");

        // Return response
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .message("Quiz added successfully!")
                        .status(true)
                        .data(createdQuiz)
                        .build(),
                HttpStatus.CREATED
        );
    }

    // Controller for get all quiz
    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse> getAll(){
        // Get logged-in user using SecurityContextHolder
        String loggedInUser = getCurrentUsername();
        log.debug(loggedInUser);
        List<Quiz> quizzes = this.quizService.getAllQuizzes();
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .message("Retrieved all the quizzes from database!!!!!!!!")
                        .status(true)
                        .data(quizzes)
                        .build(),
                HttpStatus.OK
        );

    }






    // Helper method to get current username
    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else if (principal instanceof String) {
            return (String) principal;
        }

        return null;
    }
}