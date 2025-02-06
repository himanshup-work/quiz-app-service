package com.quizapp.constants;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class SqlScriptsFilePath {
    public static final String DELETE_USER_SCRIPT_FILE_PATH = "sql/delete-user-by-user-id.sql";
    public static final String SELECT_USER_BY_EMAIL_SCRIPT_FILE_PATH = "sql/select-user-by-email.sql";
    public static final String SELECT_USER_SCRIPT_FILE_PATH = "sql/select-user-by-user-id.sql";
    public static final String INSERT_USER_SCRIPT_FILE_PATH = "sql/insert-user.sql";
    public static final String SELECT_USER_BY_EMAIL_OR_USERNAME_SCRIPT_FILE_PATH = "sql/select-user-by-email-or-username.sql";
    public static final String INSERT_QUIZ_SCRIPT_FILE_PATH = "sql/insert-or-update-quiz.sql";
    public static final String INSERT_QUESTION_SCRIPT_FILE_PATH = "sql/insert-or-update-question.sql";
    public static final String INSERT_OPTION_SCRIPT_FILE_PATH = "sql/insert-or-update-option.sql";
}
