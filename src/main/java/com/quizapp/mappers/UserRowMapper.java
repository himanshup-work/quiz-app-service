package com.quizapp.mappers;

import com.quizapp.ingestion.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
//        User user = new User();
//        user.setUserId(rs.getString("user_id"));
//        user.setFullName(rs.getString("full_name"));
//        user.setEmail(rs.getString("email"));
//        user.setUsername(rs.getString("user_name"));
//        user.setPassword(rs.getString("password"));
//        user.setImage(rs.getBytes("image"));

        return  User.builder()
                .userId(rs.getString("user_id"))
                .fullName(rs.getString("full_name"))
                .email(rs.getString("email"))
                .username(rs.getString("user_name"))
                .password(rs.getString("password"))
                .image(rs.getBytes("image"))
                .build();
    }
}
