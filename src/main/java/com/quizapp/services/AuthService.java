package com.quizapp.services;

import com.quizapp.ingestion.User;

public interface AuthService {
    User register(User user);
}
