package com.demo.mysql.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class MysqlConfig {

    @Bean(name = "OneDataSource")
    @Qualifier("OneDataSource")
    @Primary
    @ConfigurationProperties(prefix="spring.datasource.one")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "TwoDataSource")
    @Qualifier("TwoDataSource")
    @ConfigurationProperties(prefix="spring.datasource.two")
    public DataSource secondaryDataSource() {
        return DataSourceBuilder.create().build();
    }


    @Bean(name="OneJdbcTemplate")
    public JdbcTemplate primaryJdbcTemplate (
            @Qualifier("OneDataSource")  DataSource dataSource ) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name="TwoJdbcTemplate")
    public JdbcTemplate  secondaryJdbcTemplate(
            @Qualifier("TwoDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
