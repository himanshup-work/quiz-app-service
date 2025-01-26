package com.quizapp.services.impl;

import com.quizapp.ingestion.User;
import com.quizapp.repositories.UserRepository;
import com.quizapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public User getUserById(String userId) {
        return this.userRepository.findUserById(userId);
    }
}
