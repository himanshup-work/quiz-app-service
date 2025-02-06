package com.quizapp.controllers;

import com.quizapp.exceptions.ResourceNotFoundException;
import com.quizapp.ingestion.User;
import com.quizapp.services.UserService;
import com.quizapp.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable("userId") String userId) {
        // Log the request for traceability
        log.info("Fetching user with ID: {}", userId);

        // Retrieve user, which will throw ResourceNotFoundException if not found
        User user = userService.getUserById(userId);

        return ResponseEntity.ok(ApiResponse.builder()
                .status(true)
                .message("User found successfully")
                .data(user)
                .build());
    }

    @PutMapping("/edit")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody User user) {
        // Validate user existence before update
        User existingUser = userService.getUserByEmailOrUsername(user.getEmail());
        if (existingUser == null) {
            log.warn("Attempted to update non-existent user with email: {}", user.getEmail());
            throw new ResourceNotFoundException("User", "email", user.getEmail());
        }

        // Preserve existing user ID if not provided
        if (user.getUserId() == null) {
            user.setUserId(existingUser.getUserId());
        }

        // Log update attempt
        log.info("Updating user with email: {}", user.getEmail());

        // Perform update
        User updatedUser = userService.saveOrUpdateUser(user);

        log.info("User updated successfully: {}", updatedUser.getEmail());

        return ResponseEntity.ok(ApiResponse.builder()
                .message("User updated successfully")
                .status(true)
                .data(updatedUser)
                .build());
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") String userId) {
        // Log deletion attempt
        log.info("Attempting to delete user with ID: {}", userId);

        // Verify user exists before deletion
        User userToDelete = userService.getUserById(userId);

        // Perform deletion
        userService.deleteUser(userId);

        log.info("User deleted successfully: {}", userId);

        return ResponseEntity
                .ok(ApiResponse.builder()
                        .message("User deleted successfully")
                        .status(true)
                        .data(null)
                        .build());
    }
}