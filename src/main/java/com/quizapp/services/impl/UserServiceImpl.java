package com.quizapp.services.impl;

import com.quizapp.exceptions.DatabaseException;
import com.quizapp.exceptions.ResourceNotFoundException;
import com.quizapp.ingestion.User;
import com.quizapp.repositories.UserRepository;
import com.quizapp.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User getUserById(String userId) {
        log.info("Fetching user by ID: {}", userId);
        try {
            User user = this.userRepository.findUserById(userId);
            log.debug("User found: {}", user);
            return user;
        } catch (EmptyResultDataAccessException e) {
            log.error("User not found with ID: {}", userId, e);
            throw new ResourceNotFoundException("User", "user id", userId);
        } catch (DataAccessException e) {
            log.error("Database error while finding user by ID: {}", userId, e);
            throw new DatabaseException("Database error while finding user by ID: " + e.getMessage());
        }
    }

    @Override
    public User saveOrUpdateUser(User user) {
        try {
            int rowsAffected = this.userRepository.saveOrUpdateUser(user);
            if (rowsAffected == 0) {
                log.error("Failed to save or update user: {}", user.getEmail());
                throw new RuntimeException("Failed to save or update user.");
            }
            log.info("User saved or updated successfully: {}", user.getEmail());
            return getUserById(user.getUserId());
        } catch (DataAccessException e) {
            log.error("Database error while saving or updating user: {}", user.getEmail(), e);
            throw new DatabaseException("Database error while saving or updating user: " + e.getMessage());
        }
    }

    @Override
    public boolean userExist(String email) {
        log.info("Checking if user exists with email: {}", email);
        boolean exists = this.userRepository.userExist(email);
        log.debug("User existence check result for email {}: {}", email, exists);
        return exists;
    }

    @Override
    public void deleteUser(String userId) {
        log.info("Deleting user with ID: {}", userId);
        int rowsAffected = this.userRepository.deleteUserById(userId);

        if (rowsAffected == 0) {
            log.error("User not found with ID: {}", userId);
            throw new ResourceNotFoundException("User", "user id", userId);
        }
        log.info("User deleted successfully with ID: {}", userId);
    }

    @Override
    public User getUserByEmail(String email) {
        log.info("Fetching user by email: {}", email);
        try {
            User user = this.userRepository.findUserByEmail(email);
            log.debug("User found: {}", user);
            return user;
        } catch (EmptyResultDataAccessException e) {
            log.error("User not found with email: {}", email, e);
            throw new ResourceNotFoundException("User", "username", email);
        } catch (DataAccessException e) {
            log.error("Database error while finding user by email: {}", email, e);
            throw new DatabaseException("Database error while finding user by email: " + e.getMessage());
        }
    }

    @Override
    public User getUserByEmailOrUsername(String emailOrUsername) {
        log.info("Fetching user by email or username: {}", emailOrUsername);
        try {
            User user = this.userRepository.findUserByEmailOrUsername(emailOrUsername);
            log.debug("User found: {}", user);
            return user;
        } catch (EmptyResultDataAccessException e) {
            log.error("User not found with email or username: {}", emailOrUsername, e);
            throw new ResourceNotFoundException("User", "email or username", emailOrUsername);
        } catch (DataAccessException e) {
            log.error("Database error while finding user by email: {}", emailOrUsername, e);
            throw new DatabaseException("Database error while finding user by email or username: " + e.getMessage());
        }
    }
}