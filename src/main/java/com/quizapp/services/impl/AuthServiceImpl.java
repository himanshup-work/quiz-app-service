package com.quizapp.services.impl;

import com.quizapp.constants.UserRole;
import com.quizapp.ingestion.User;
import com.quizapp.services.AuthService;
import com.quizapp.services.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void saveAdminUser() {
        User user = User.builder()
                .userId("admin@himanshu")
                .firstName("Himanshu")
                .lastName("Pal")
                .email("himanshu@admin.com")
                .username("admin_himanshu")
                .password(this.passwordEncoder.encode("admin@1999"))
                .role(UserRole.ADMIN.name())
                .build();

        if (this.userService.userExist(user.getEmail())){
            log.info("User with email {} already exists", user.getEmail());
        }else {
            this.userService.saveOrUpdateUser(user);
            log.info("User with email {} created", user.getEmail());
        }
    }

    @Override
    public User register(User user) {
        log.debug("Generating new user ID for: {}", user.getEmail());
        String userId = UUID.randomUUID().toString();
        log.debug("Generated user ID: {}", userId);
        user.setUserId(userId);
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        user.setRole(UserRole.USER.name());
        log.debug("Creating new user with email: {}", user.getEmail());
        val createdUser = this.userService.saveOrUpdateUser(user);
        log.debug("User created successfully with email: {}", user.getEmail());
        return createdUser;
    }
}
