package com.quizapp.ingestion;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class User {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String bio;
    private String password;
    private byte[] image;
    private String role;
    private List<Quiz> quizzes;
}
