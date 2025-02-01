package com.quizapp.controllers;

import com.quizapp.exceptions.ResourceNotFoundException;
import com.quizapp.ingestion.User;
import com.quizapp.services.UserService;
import com.quizapp.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getUser(@PathVariable("userId") String userId) {
        User user = this.userService.getUserById(userId); // Service call
        return ResponseEntity.ok(ApiResponse.builder()
                .status(true)
                .message("User found successfully with ID: " + userId)
                .data(user)
                .build());
    }


    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createUser(@RequestBody User user) {
        System.out.println(user.getPassword());
        if (userService.userExist(user.getEmail())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT) // 409 Conflict
                    .body(ApiResponse.builder()
                            .status(false)
                            .message("User already exists with email: " + user.getEmail())
                            .data(null)
                            .build());
        }

        // Since user doesn't exist, create a new user
        User createdUser = userService.saveOrUpdateUser(user);

        return ResponseEntity
                .status(HttpStatus.CREATED) // 201 Created
                .body(ApiResponse.builder()
                        .status(true)
                        .message("User created successfully with ID: " + createdUser.getUserId())
                        .data(createdUser)
                        .build());
    }



    @PutMapping("/edit")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody User user) {
        if (!userService.userExist(user.getEmail())) {
            // If user doesn't exist, throw a ResourceNotFoundException
            throw new ResourceNotFoundException("User", "email", user.getEmail());
        }

        // Since user exists, update it
        User updatedUser = userService.saveOrUpdateUser(user);

        return ResponseEntity.ok(ApiResponse.builder()
                .message("User updated successfully.")
                .status(true)
                .data(updatedUser)
                .build());
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") String userId) {
        this.userService.deleteUser(userId);  // Service call
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.builder()
                        .message("User deleted successfully.")
                        .status(true)
                        .data(null)
                        .build());
    }


}
