package com.quizapp.controllers;

import com.quizapp.auth.AuthRequest;
import com.quizapp.auth.UserDetailsServiceImpl;
import com.quizapp.exceptions.ResourceNotFoundException;
import com.quizapp.ingestion.User;
import com.quizapp.services.UserService;
import com.quizapp.utils.ApiResponse;
import com.quizapp.utils.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final UserService userService;

    public AuthController(JwtUtil jwtUtil, AuthenticationManager authManager,
                          UserDetailsServiceImpl userDetailsService, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.authManager = authManager;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody AuthRequest request) {
        User user = userService.getUserByEmail(request.getEmail());
        if (user == null) {
            throw new ResourceNotFoundException("User", "email", request.getEmail());
        }

        authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = jwtUtil.generateToken(userDetails, user);

        return ResponseEntity.ok(ApiResponse.builder()
                .message("User logged in successfully!")
                .status(true)
                .data(token)
                .build());
    }
}