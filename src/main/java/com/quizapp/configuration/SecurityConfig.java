package com.quizapp.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quizapp.auth.JwtAuthenticationEntryPoint;
import com.quizapp.auth.JwtAuthenticationFilter;
import com.quizapp.auth.UserDetailsServiceImpl;
import com.quizapp.constants.UserRole;
import com.quizapp.utils.ApiResponse;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    public static final String[] PUBLIC_URLS = {
            "/auth/**"
    };
    private final JwtAuthenticationEntryPoint entryPoint;
    private final JwtAuthenticationFilter authenticationFilter;
    private final UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(JwtAuthenticationEntryPoint entryPoint, @Lazy JwtAuthenticationFilter authenticationFilter, UserDetailsServiceImpl userDetailsService) {
        this.entryPoint = entryPoint;
        this.authenticationFilter = authenticationFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            @NonNull AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(
    ) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        http
                // Enable CORS support
                .cors(Customizer.withDefaults())

                // Disable CSRF for stateless API
                .csrf(AbstractHttpConfigurer::disable)

                // Configure authorization rules
                .authorizeHttpRequests(auth -> auth
                        // Public URLs accessible to everyone
                        .requestMatchers(PUBLIC_URLS).permitAll()

                        // Admin-only endpoints
                        .requestMatchers("/admin/**").hasAuthority(UserRole.ADMIN.name())

                        // User-specific endpoints
                        .requestMatchers("/user/**").hasAnyAuthority(
                                UserRole.USER.name(),
                                UserRole.ADMIN.name()  // Optional: allow admins to access user endpoints
                        )

                        // Ensure all other endpoints require authentication
                        .anyRequest().authenticated()
                )

                // Configure stateless session management
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Configure authentication provider
                .authenticationProvider(authenticationProvider())

                // Add custom authentication filter before default
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)

                // Configure exception handling
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(entryPoint)
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpStatus.FORBIDDEN.value());
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            response.getWriter().write(
                                    objectMapper.writeValueAsString(
                                            ApiResponse.builder()
                                                    .status(false)
                                                    .message("Access denied")
                                                    .build()
                                    )
                            );
                        })
                );

        return http.build();
    }
}