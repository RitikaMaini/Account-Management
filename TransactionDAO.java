package com.account.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class TransactionDAO {

    private static JdbcTemplate jdbcTemplate;

    public TransactionDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper to map ResultSet to Transaction object
    private static final RowMapper<Transaction> TRANSACTION_ROW_MAPPER = new RowMapper<Transaction>() {
        @Override
        public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Transaction(
                    rs.getLong("senderaccountnumber"),
                    rs.getLong("receiveraccountnumber"),
                    rs.getDouble("amount")
            );
        }
    };

    public void createTransactionTable() {
        String query = "CREATE TABLE Transactionn ("
                + "Id BIGINT AUTO_INCREMENT PRIMARY KEY, "
                + "senderaccountnumber BIGINT NOT NULL, "
                + "receiveraccountnumber BIGINT NOT NULL, "
                + "amount DECIMAL(10, 2) NOT NULL"
                + ")";
        jdbcTemplate.execute(query);
        System.out.println("Transaction table is created successfully.");
    }

    public List<Transaction> getAllTransactions(long accountNumber) {
        String query = "SELECT * FROM Transactionn WHERE senderaccountnumber = ?";
        return jdbcTemplate.query(query, new Object[]{accountNumber}, TRANSACTION_ROW_MAPPER);
    }

    public void updateBalance(long accountNumber, double newBalance) throws SQLException {
        String query = "UPDATE Account SET balance = ? WHERE accountnumber = ?";
        jdbcTemplate.update(query, newBalance, accountNumber);
    }

    public void logTransaction(long senderAccountNumber, long receiverAccountNumber, double amount) {
        // Implement transaction logging logic
        String query = "INSERT INTO Transactions (senderAccountNumber, receiverAccountNumber, amount) VALUES (?, ?, ?)";
        jdbcTemplate.update(query, senderAccountNumber, receiverAccountNumber, amount);
    }
	

}
