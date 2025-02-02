package com.quizapp.auth;

import com.quizapp.ingestion.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    private final User user;
    private final String loginIdentifier; // Stores the email or username used for login

    public CustomUserDetails(User user, String loginIdentifier) {
        this.user = user;
        this.loginIdentifier = loginIdentifier;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = (user.getRole() != null && !user.getRole().isEmpty())
                ? user.getRole()
                : "ROLE_USER";
        if (!role.startsWith("ROLE_")) {
            role = "ROLE_" + role;
        }
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        // Return the login identifier (email or username)
        return loginIdentifier;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}