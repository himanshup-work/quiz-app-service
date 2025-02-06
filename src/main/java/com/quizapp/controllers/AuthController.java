package com.quizapp.controllers;

import com.quizapp.auth.AuthRequest;
import com.quizapp.auth.AuthResponse;
import com.quizapp.auth.UserDetailsServiceImpl;
import com.quizapp.ingestion.User;
import com.quizapp.services.AuthService;
import com.quizapp.services.UserService;
import com.quizapp.utils.ApiResponse;
import com.quizapp.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody AuthRequest request) {
        // Check if user exists
        User existingUser = userService.getUserByEmailOrUsername(request.getEmailOrUsername());
        if (existingUser == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.builder()
                            .status(false)
                            .message("User not found with this email/username. Please sign up.")
                            .data(null)
                            .build());
        }

        try {
            // Attempt authentication
            authManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getEmailOrUsername(),
                    request.getPassword()
            ));

            // Load user details and generate token
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmailOrUsername());
            String token = jwtUtil.generateToken(userDetails, existingUser);

            return ResponseEntity.ok(ApiResponse.builder()
                    .status(true)
                    .message("User logged in successfully!")
                    .data(AuthResponse.builder()
                            .token(token)
                            .userId(existingUser.getUserId())
                            .userRole(existingUser.getRole())
                            .build())
                    .build());

        } catch (BadCredentialsException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.builder()
                            .status(false)
                            .message("Invalid credentials. Please try again.")
                            .data(null)
                            .build());
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signup(@RequestBody User user) {
        // Check if email or username already exists
        if (userService.userExist(user.getEmail())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(ApiResponse.builder()
                            .status(false)
                            .message("Email is already in use.")
                            .data(null)
                            .build());
        }

        if (userService.userExist(user.getUsername())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(ApiResponse.builder()
                            .status(false)
                            .message("Username is already in use.")
                            .data(null)
                            .build());
        }

        // Create new user
        User createdUser = authService.register(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.builder()
                        .status(true)
                        .message("User created successfully")
                        .data(createdUser)
                        .build());
    }
}