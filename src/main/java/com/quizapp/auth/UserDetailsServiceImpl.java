package com.quizapp.auth;

import com.quizapp.ingestion.User;
import com.quizapp.services.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String loginIdentifier) throws UsernameNotFoundException {
        // Fetch user by email or username
        User user = this.userService.getUserByEmailOrUsername(loginIdentifier);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with this email or username!!");
        }
        return new CustomUserDetails(user, loginIdentifier);
    }
}