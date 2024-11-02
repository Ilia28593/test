package com.example.beck_spring_my_progect.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Data
@Component
@ConfigurationProperties(prefix = "spring.datasource")
public class DataBaseProperty {
    private String username;
    private String password;
    private String driver;
    private String url;

    @Bean(name = DatabaseConstant.POSTGRES_DATA_SOURCE)
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .driverClassName(driver)
                .url(url)
                .username(username)
                .password(password)
                .build();
    }

    @Primary
    @Bean(name = DatabaseConstant.POSTGRES_JDBC)
    public JdbcTemplate jdbcTemplate(@Qualifier(DatabaseConstant.POSTGRES_DATA_SOURCE) DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
