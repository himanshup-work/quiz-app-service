package com.quizapp.utils;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class DatabaseCheckRunner implements CommandLineRunner {

    private final DataSource dataSource;

    public DatabaseCheckRunner(@NonNull @Qualifier("serviceDataSource")DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run(String... args) throws Exception {
        try (var connection = dataSource.getConnection()) {
            System.out.println("Database connection is successful: " + connection.getMetaData().getURL());
        } catch (Exception e) {
            throw new RuntimeException("Failed to connect to the database", e);
        }
    }
}
