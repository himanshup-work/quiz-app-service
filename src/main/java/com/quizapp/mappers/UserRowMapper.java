package com.quizapp.mappers;

import com.quizapp.ingestion.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {

        return  User.builder()
                .userId(rs.getString("user_id"))
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .email(rs.getString("email"))
                .username(rs.getString("user_name"))
                .password(rs.getString("password"))
                .bio(rs.getString("bio"))
                .image(rs.getBytes("image"))
                .role(rs.getString("role"))
                .build();
    }
}
