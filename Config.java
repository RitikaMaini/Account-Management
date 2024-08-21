package com.account.jdbc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class Config {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/account_management");
        dataSource.setUsername("root");
        dataSource.setPassword("ritika@2000");
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
    @Bean
    public AccountDAO accountDAO() {
        return new AccountDAO(jdbcTemplate());
    }
    @Bean
    public TransactionDAO transactionDAO() {
        return new TransactionDAO(jdbcTemplate());
    }
}
