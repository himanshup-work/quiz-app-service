package com.quizapp.controllers;

import com.quizapp.ingestion.Option;
import com.quizapp.ingestion.Question;
import com.quizapp.ingestion.Quiz;
import com.quizapp.services.OptionService;
import com.quizapp.services.QuestionService;
import com.quizapp.services.QuizService;
import com.quizapp.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/quiz")
@CrossOrigin("**")
public class QuizController {
    private final QuizService quizService;
    private final QuestionService questionService;
    private final OptionService optionService;

    public QuizController(QuizService quizService, QuestionService questionService, OptionService optionService) {
        this.quizService = quizService;
        this.questionService = questionService;
        this.optionService = optionService;
    }

    @PostMapping("/create")
    @Transactional
    public ResponseEntity<ApiResponse> createQuiz(@RequestBody Quiz quiz, Principal principal) {
        // Get logged-in user dynamically
        String loggedInUser = principal.getName();

        // Generate unique quiz ID
        String quizId = UUID.randomUUID().toString();
        quiz.setQuizId(quizId);
        quiz.setCreatedBy(loggedInUser);

        // Prepare batch inserts for questions and options
        List<Question> questions = new ArrayList<>();
        List<Option> options = new ArrayList<>();

        for (Question question : quiz.getQuestions()) {
            // Assign Quiz ID to Question
            question.setQuizId(quizId);
            question.setQuestionId(UUID.randomUUID().toString());
            questions.add(question);

            for (Option option : question.getOptions()) {
                // Assign Question ID to Option
                option.setQuestionId(question.getQuestionId());
                options.add(option);
            }
        }

        // Save Quiz
        Quiz createdQuiz = this.quizService.createQuiz(quiz);

        // Batch insert Questions
        this.questionService.saveOrUpdateQuestions(questions);

        // Batch insert Options
        this.optionService.saveOrUpdateOptions(options);

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

}
