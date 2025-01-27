package com.quizapp.constants;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class SqlScriptsFilePath {
    public final String SELECT_USER_SCRIPT_FILE_PATH = "sql/select-user.sql";
    public final String INSERT_USER_SCRIPT_FILE_PATH = "sql/insert-user.sql";
}
