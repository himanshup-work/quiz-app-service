package com.quizapp;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "quiz.app.service")
public class QuizApplicationProperties {

    private DataSourceProperties dataSource;

    @Getter
    @RequiredArgsConstructor
    public static class DataSourceProperties {
        private final String driverClassName;
        private final String url;
        private final String username;
        private final String password;
        private final String schema;

    }

}
