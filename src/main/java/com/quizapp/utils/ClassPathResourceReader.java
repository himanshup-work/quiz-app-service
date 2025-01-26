package com.quizapp.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import static java.text.MessageFormat.format;

@Component
@Slf4j
public class ClassPathResourceReader {

    /**
     * Reads a file from the classpath and returns its content as a string.
     *
     * @param filePath The path to the SQL file in the classpath.
     * @return The content of the SQL file as a string.
     */
    public String readSqlFile(String filePath) {
        ClassPathResource resource = new ClassPathResource(filePath);

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }catch (IOException exception){
            log.error(format("Unable to find sql file at given path: {0}", filePath));
            throw new RuntimeException(format("Unable to find sql file at given path: {0}", filePath));
        }
    }
}
