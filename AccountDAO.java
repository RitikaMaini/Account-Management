package com.account.jdbc;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AccountDAO {

    private JdbcTemplate jdbcTemplate;

    public AccountDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /* RowMapper to map ResultSet to Account object
    private static final RowMapper<Account> ACCOUNT_ROW_MAPPER = new RowMapper<Account>() {
        @Override
        public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Account(
                    rs.getString("name"),
                    rs.getLong("AccountNumber"),
                    rs.getDouble("Balance"),
                    rs.getString("pin")
            );
        }
    };*/

    public void createAccountTable() {
        String query = "CREATE TABLE Account ("
                + "name VARCHAR(20),"
                + "accountnumber BIGINT PRIMARY KEY,"
                + "balance DECIMAL(10, 2),"
                + "pin VARCHAR(20)"
                + ")";
        jdbcTemplate.execute(query);
        System.out.println("Account table is created successfully.");
    }

    public void addAccount(Account account) {
        String query = "INSERT INTO Account (name, accountnumber, balance, pin) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(query, account.getName(), account.getAccountNumber(), account.getBalance(), account.getPin());
        System.out.println("Account Registration is successful.");
    }
    Account account;
    public void showBalance(long accountNumber, String pin) {
    	
        String query = "SELECT balance FROM Account WHERE accountnumber = ? AND pin = ?";
        try {
        Double balance = jdbcTemplate.queryForObject(
        	    query,                         // The SQL query
        	    new Object[]{accountNumber, pin},
        	    Double.class );  
        System.out.println(balance);
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 /*  
			if(accountnumber==accountNumber) {
				if(account.getPin()==pin) {
					double balance=jdbcTemplate.queryForObject(query, Double.class);
					System.out.println(balance);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
       }
    

    public void updateDeposit(long accountNumber, String pin, double amount) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be greater than zero.");
            return;
        }

        String query = "UPDATE Account SET balance = balance + ? WHERE accountnumber = ? AND pin = ?";
        int rowsAffected = jdbcTemplate.update(query, amount, accountNumber, pin);
        if (rowsAffected > 0) {
            System.out.println("Deposit successful.");
        } else {
            System.out.println("Invalid account number or pin. Deposit failed.");
        }
    }

    public void updateWithdraw(long accountNumber, String pin, double amount) {
        double balance = getBalance(accountNumber, pin);
        if (balance < 0) {
            System.out.println("Invalid account number or pin.");
            return;
        }

        if (amount <= 0 || amount > balance) {
            System.out.println("Enter a valid amount (greater than 0 and less than balance).");
            return;
        }

        String query = "UPDATE Account SET balance = balance - ? WHERE accountnumber = ? AND pin = ?";
        int rowsAffected = jdbcTemplate.update(query, amount, accountNumber, pin);
        if (rowsAffected > 0) {
            System.out.println("Withdrawal successful.");
        } else {
            System.out.println("Failed to withdraw. Please check account details.");
        }
    }

    public double getBalance(long accountNumber, String pin) {
        String query = "SELECT balance FROM Account WHERE accountnumber = ? AND pin = ?";
        try {
        return jdbcTemplate.queryForObject(query, new Object[]{accountNumber, pin}, Double.class);
        }  catch (Exception e) {
            
            throw new RuntimeException("Error retrieving balance", e);
        }
    }
    public Double getBalance1(long receiverAccountNumber) {
        String query = "SELECT balance FROM Account WHERE accountnumber = ?";
        
        try {
            return jdbcTemplate.queryForObject(
                query,
                new Object[]{receiverAccountNumber},
                Double.class
            );
        } catch (EmptyResultDataAccessException e) {
            return null; 
        } catch (Exception e) {
            // Handle other potential exceptions (e.g., database errors)
            throw new RuntimeException("Error retrieving balance", e);
        }
    }
    public void updateBalance(long accountNumber, double newBalance) throws SQLException {
        String query = "UPDATE Account SET balance = ? WHERE accountnumber = ?";
        jdbcTemplate.update(query, newBalance, accountNumber);
    }

	/*public double getBalance1(long receiverAccountNumber) {
	        String query = "SELECT balance FROM Account WHERE accountnumber = ?";
	        
	        Double balance = jdbcTemplate.queryForObject(
	        	    query,                         // The SQL query
	        	    new Object[]{receiverAccountNumber},
	        	    Double.class );
	        return balance;

		// TODO Auto-generated method stub
	}*/
}
