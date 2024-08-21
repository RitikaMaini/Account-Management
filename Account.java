package com.account.jdbc;

public class Account {
	private String name;
	private long AccountNumber;
	private Double Balance;
	private String pin;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getAccountNumber() {
		return AccountNumber;
	}
	public void setAccountNumber(long accountNumber) {
		AccountNumber = accountNumber;
	}
	public Double getBalance() {
		return Balance;
	}
	public void setBalance(Double balance) {
		Balance = balance;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public Account(String name, long accountNumber, Double balance, String pin) {
		super();
		this.name = name;
		AccountNumber = accountNumber;
		Balance = balance;
		this.pin = pin;
	}
}
