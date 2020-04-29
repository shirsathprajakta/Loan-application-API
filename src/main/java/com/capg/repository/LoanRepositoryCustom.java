package com.capg.repository;

import java.util.List;

import com.capg.model.Bank;
import com.capg.model.Transaction;

public interface LoanRepositoryCustom {

	public Bank createAccount(Bank account);
	public boolean validateUser(String username,String password);
	public boolean validateRegister(String phoneno,String email);
	public boolean validateAccount(long accountNo);
	public String applyLoan(long accountNo,Double loanAmount);
	public Double showBalance(long accountNo);
	public Bank getCust(String username,String passward);
	public String payemi(long accountNo,double emiAmout);
	public String deposit(long accountNo,double Amount);
	public String foreClose(long accountNo);
	public int calEMI(long loanAmount, int period, int rate);
	public List<Transaction> printTransaction(long accountNo);
}
