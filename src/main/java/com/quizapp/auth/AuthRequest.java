package com.quizapp.auth;

import lombok.Data;

@Data
public class AuthRequest {
    private String emailOrUsername;
    private String password;
}
