package com.account.jdbc;

/**
 * Hello world!
 *
 */
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.Scanner;

public class App {
    private static final Scanner scanner = new Scanner(System.in);
     static ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
     static AccountDAO accountDAO = context.getBean(AccountDAO.class);
     static TransactionDAO transactionDAO = context.getBean(TransactionDAO.class);

    public static void main(String[] args) throws SQLException {
        while (true) {
        	System.out.println("Menu:");
            System.out.println("1. Create Account");
            System.out.println("2. Show Balance");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. Transfer Funds");
            System.out.println("6. Show Transactions");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    showBalance();
                    break;
                case 3:
                    deposit();
                    break;
                case 4:
                    withdraw();
                    break;
                case 5:
                    transferFunds();
                    break;
                case 6:
                    showTransactions();
                    break;
                case 7:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    

    private static void createAccount() {
        System.out.print("Enter Account Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Account Number: ");
        long accountNumber = scanner.nextLong();
        System.out.print("Enter Balance: ");
        double balance = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Pin: ");
        String pin = scanner.nextLine();
        System.out.print("Confirm Pin: ");
        String confirmPin = scanner.nextLine();

        if (pin.equals(confirmPin)) {
            Account account = new Account(name, accountNumber, balance, pin);
            accountDAO.addAccount(account);
            System.out.println("Account created successfully.");
        } else {
            System.out.println("Pins do not match. Account creation failed.");
        }
    }

    private static void showBalance() {
        System.out.print("Enter Account Number: ");
        long accountNumber = scanner.nextLong();
        scanner.nextLine(); 
        System.out.print("Enter Pin: ");
        String pin = scanner.nextLine();
        accountDAO.showBalance(accountNumber, pin);
    }

    private static void deposit() {
        System.out.print("Enter Account Number: ");
        long accountNumber = scanner.nextLong();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Pin: ");
        String pin = scanner.nextLine();
        System.out.print("Enter Deposit Amount: ");
        double amount = scanner.nextDouble();
        accountDAO.updateDeposit(accountNumber, pin, amount);
    }

    private static void withdraw() {
        System.out.print("Enter Account Number: ");
        long accountNumber = scanner.nextLong();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Pin: ");
        String pin = scanner.nextLine();
        System.out.print("Enter Withdraw Amount: ");
        double amount = scanner.nextDouble();
        accountDAO.updateWithdraw(accountNumber, pin, amount);
    }

    private static void transferFunds() throws SQLException {
        System.out.print("Enter Sender Account Number: ");
        long senderAccountNumber = scanner.nextLong();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Sender Pin: ");
        String senderPin = scanner.nextLine();
        System.out.print("Enter Receiver Account Number: ");
        long receiverAccountNumber = scanner.nextLong();
        System.out.print("Enter Transfer Amount: ");
        double amount = scanner.nextDouble();

        double senderBalance = accountDAO.getBalance(senderAccountNumber, senderPin);
		if (senderBalance < 0) {
		    System.out.println("Invalid sender account number or pin.");
		    return;
		}
		if (amount > senderBalance) {
		    System.out.println("Insufficient funds.");
		    return;
		}

		transactionDAO.updateBalance(senderAccountNumber, senderBalance - amount);
        Double receiverBalance = accountDAO.getBalance1(receiverAccountNumber);

        transactionDAO.updateBalance(receiverAccountNumber, receiverBalance + amount);
        transactionDAO.logTransaction(senderAccountNumber, receiverAccountNumber, amount);

        System.out.println("Transfer successful.");    }

    private static void showTransactions() {
        System.out.print("Enter Account Number: ");
        long accountNumber = scanner.nextLong();
        scanner.nextLine(); // Consume newline
        var transactions = transactionDAO.getAllTransactions(accountNumber);
        if (transactions.isEmpty()) {
            System.out.println("No transactions found for this account.");
        } else {
            for (Transaction transaction : transactions) {
                System.out.println(transaction);
            }
        }
    }
}


