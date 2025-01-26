package com.quizapp.configuration;

import com.quizapp.QuizApplicationProperties;
import lombok.NonNull;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;


@Configuration
public class DataSourceConfigurationBean {
    @Bean(name = "serviceDataSource")
    public DataSource serviceDataSource(
            @NonNull QuizApplicationProperties properties
    ){
        val props = properties.getDataSource();
        val dataSourceProps = new DriverManagerDataSource();
        dataSourceProps.setDriverClassName(props.getDriverClassName());
        dataSourceProps.setUrl(props.getUrl());
        dataSourceProps.setUsername(props.getUsername());
        dataSourceProps.setPassword(props.getPassword());
        dataSourceProps.setSchema(props.getSchema());

        return dataSourceProps;
    }
}
