package com.quizapp.repositories;

import com.quizapp.constants.SqlScriptsFilePath;
import com.quizapp.ingestion.User;
import com.quizapp.mappers.UserRowMapper;
import com.quizapp.utils.ClassPathResourceReader;
import lombok.NonNull;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;
    private final ClassPathResourceReader resourceReader;

    public UserRepository(
            @NonNull @Qualifier("jdbcTemplate") JdbcTemplate jdbcTemplate,
            @NonNull ClassPathResourceReader resourceReader) {
        this.jdbcTemplate = jdbcTemplate;
        this.resourceReader = resourceReader;
    }

    @SuppressWarnings("deprecation")
    public User findUserById(String userId) {
        log.debug("Fetching user by ID: {}", userId);
        val sqlTemplate = this.resourceReader.readSqlFile(SqlScriptsFilePath.SELECT_USER_SCRIPT_FILE_PATH);
        try {
            return this.jdbcTemplate.queryForObject(sqlTemplate, new Object[]{userId}, new UserRowMapper());
        } catch (DataAccessException e) {
            log.error("Error fetching user by ID: {}", userId, e);
            throw e; // Re-throw the exception for handling in the service layer
        }
    }

    public int saveOrUpdateUser(User user) {
        log.debug("Saving or updating user with user id: {}", user.getUserId());
        String sqlTemplate = this.resourceReader.readSqlFile(SqlScriptsFilePath.INSERT_USER_SCRIPT_FILE_PATH);
        try {
            return this.jdbcTemplate.update(sqlTemplate,
                    user.getUserId(),
                    user.getFullName(),
                    user.getEmail(),
                    user.getUsername(),
                    user.getPassword(),
                    user.getImage(),
                    user.getRole());
        } catch (DataAccessException e) {
            log.error("Error saving or updating user with user id: {}", user.getUserId(), e);
            throw e; // Re-throw the exception for handling in the service layer
        }
    }

    public boolean userExist(String email) {
        log.debug("Checking if user exists with email: {}", email);
        val sqlTemplate = "SELECT EXISTS (SELECT 1 FROM quiz_app_service.users WHERE email = ?);";
        try {
            return jdbcTemplate.queryForObject(sqlTemplate, Boolean.class, email);
        } catch (DataAccessException e) {
            log.error("Error checking user existence for email: {}", email, e);
            throw e; // Re-throw the exception for handling in the service layer
        }
    }

    public int deleteUserById(String userId) {
        log.debug("Deleting user with ID: {}", userId);
        String sqlTemplate = this.resourceReader.readSqlFile(SqlScriptsFilePath.DELETE_USER_SCRIPT_FILE_PATH);
        try {
            return this.jdbcTemplate.update(sqlTemplate, userId);
        } catch (DataAccessException e) {
            log.error("Error deleting user with ID: {}", userId, e);
            throw e; // Re-throw the exception for handling in the service layer
        }
    }

    @SuppressWarnings("deprecation")
    public User findUserByEmail(String email) {
        log.debug("Fetching user by email: {}", email);
        val sqlTemplate = this.resourceReader.readSqlFile(SqlScriptsFilePath.SELECT_USER_BY_EMAIL_SCRIPT_FILE_PATH);
        try {
            return this.jdbcTemplate.queryForObject(sqlTemplate, new Object[]{email}, new UserRowMapper());
        } catch (DataAccessException e) {
            log.error("Error fetching user by email: {}", email, e);
            throw e; // Re-throw the exception for handling in the service layer
        }
    }

    @SuppressWarnings("deprecation")
    public User findUserByEmailOrUsername(String emailOrUsername) {
        log.debug("Fetching user by email or username: {}", emailOrUsername);
        val sqlTemplate = this.resourceReader.readSqlFile(SqlScriptsFilePath.SELECT_USER_BY_EMAIL_OR_USERNAME_SCRIPT_FILE_PATH);
        try {
            return this.jdbcTemplate.queryForObject(sqlTemplate, new Object[]{emailOrUsername,emailOrUsername}, new UserRowMapper());
        } catch (DataAccessException e) {
            log.error("Error fetching user by email or username: {}", emailOrUsername, e);
            throw e; // Re-throw the exception for handling in the service layer
        }
    }
}