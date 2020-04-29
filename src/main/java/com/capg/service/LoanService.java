package com.capg.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capg.model.Bank;
import com.capg.model.Transaction;
import com.capg.repository.LoanRepository;
import com.capg.repository.LoanRepositoryCustom;

@Service
public class LoanService implements LoanRepositoryCustom{

	@Autowired
	LoanRepository loanRepository;

	@Override
	public Bank createAccount(Bank account) {
		// TODO Auto-generated method stub
		return loanRepository.createAccount(account);
	}

	@Override
	public boolean validateUser(String username, String password) {
		// TODO Auto-generated method stub
		return loanRepository.validateUser(username, password);
	}

	@Override
	public boolean validateAccount(long accountNo) {
		// TODO Auto-generated method stub
		return loanRepository.validateAccount(accountNo);
	}

	@Override
	public String applyLoan(long accountNo, Double loanAmount) {
		// TODO Auto-generated method stub
		return loanRepository.applyLoan(accountNo,loanAmount);
	}

	@Override
	public Double showBalance(long accountNo) {
		// TODO Auto-generated method stub
		return loanRepository.showBalance(accountNo);
	}

	@Override
	public Bank getCust(String username, String passward) {
		// TODO Auto-generated method stub
		return loanRepository.getCust(username, passward);
	}

	@Override
	public String payemi(long accountNo, double emiAmout) {
		// TODO Auto-generated method stub
		return loanRepository.payemi(accountNo, emiAmout);
	}

	@Override
	public String deposit(long accountNo, double Amount) {
		// TODO Auto-generated method stub
		return loanRepository.deposit(accountNo, Amount);
	}

	@Override
	public String foreClose(long accountNo) {
		// TODO Auto-generated method stub
		return loanRepository.foreClose(accountNo);
	}

	@Override
	public int calEMI(long loanAmount, int period, int rate) {
		// TODO Auto-generated method stub
		return loanRepository.calEMI(loanAmount, period, rate);
	}

	@Override
	public List<Transaction> printTransaction(long accountNo) {
		// TODO Auto-generated method stub
		return loanRepository.printTransaction(accountNo);
	}

	@Override
	public boolean validateRegister(String phoneno, String email) {
		// TODO Auto-generated method stub
		return loanRepository.validateRegister(phoneno, email);
	}
	
	public Bank getBankException() throws BankExpception{
		
		throw new BankExpception();
	}
}
