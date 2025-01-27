package com.quizapp.repositories;

import com.quizapp.constants.SqlScriptsFilePath;
import com.quizapp.ingestion.User;
import com.quizapp.mappers.UserRowMapper;
import com.quizapp.utils.ClassPathResourceReader;
import lombok.NonNull;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    @Autowired
    private SqlScriptsFilePath scriptsFilePath;

    private final JdbcTemplate jdbcTemplate;
    private final ClassPathResourceReader resourceReader;

    public UserRepository(
            @NonNull @Qualifier("jdbcTemplate") JdbcTemplate jdbcTemplate,
            @NonNull ClassPathResourceReader resourceReader){
        this.jdbcTemplate = jdbcTemplate;
        this.resourceReader = resourceReader;
    }
    @SuppressWarnings("deprecation")
    public User findUserById(String userId) {
        val sqlTemplate = this.resourceReader.readSqlFile(scriptsFilePath.SELECT_USER_SCRIPT_FILE_PATH);
         return this.jdbcTemplate.queryForObject(sqlTemplate, new Object[]{userId}, new UserRowMapper());
    }

    @SuppressWarnings("deprecation")
    public User saveUser(User user) {
        val sqlTemplate = this.resourceReader.readSqlFile(scriptsFilePath.INSERT_USER_SCRIPT_FILE_PATH);
        return this.jdbcTemplate.queryForObject(sqlTemplate, new Object[]{
                user.getUserId(),
                user.getFullName(),
                user.getEmail(),
                user.getUsername(),
                user.getPassword(),
                user.getImage()
        }, new UserRowMapper());
    }
}
