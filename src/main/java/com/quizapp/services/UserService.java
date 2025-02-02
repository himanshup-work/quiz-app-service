package com.quizapp.services;

import com.quizapp.ingestion.User;

public interface UserService {
    User getUserById(String userId);
    User saveOrUpdateUser(User user);
    boolean userExist(String email);
    void deleteUser(String userId);
    User getUserByEmail(String email);
    User getUserByEmailOrUsername(String emailOrUsername);
}
