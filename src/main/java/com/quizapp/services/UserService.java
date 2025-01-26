package com.quizapp.services;

import com.quizapp.ingestion.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User getUserById(String userId);
}
