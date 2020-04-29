package com.capg.repository;


import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Transactional;

import com.capg.model.Bank;
import com.capg.model.Transaction;
import com.capg.service.BankExpception;
import com.capg.service.LoanService;

public class LoanRepositoryImpl implements LoanRepositoryCustom{

	@PersistenceContext
	private EntityManager entityManger;
	
	@Transactional
	@Override
	public Bank createAccount(Bank account) {
		// TODO Auto-generated method stub
		account.setAccno((long)(Math.random()*100000));
		
		int query = entityManger.createNativeQuery("insert into bank values(?,?,?,?,?,?,?,?,?)")
				.setParameter(1, account.getAccno())
				.setParameter(2, account.getAge())
				.setParameter(3, account.getBalance())
				.setParameter(4, account.getEmail())
				.setParameter(5, account.getLoanamount())
				.setParameter(6, account.getName())
				.setParameter(7, account.getPassword())
				.setParameter(8, account.getPhoneno())
				.setParameter(9, account.getUsername())
				.executeUpdate();
		
		if(query>0) {
			entityManger.createNativeQuery("insert into transaction (account_no,message) values (?,?)")
				.setParameter(1, account.getAccno())
				.setParameter(2, "Create Account for "+account.getAccno()+" on "+LocalDate.now())
				.executeUpdate();
			return account;
		}
		return null;
	}

	@Override
	public boolean validateUser(String username, String password) {
		// TODO Auto-generated method stub
		try {
			
			TypedQuery<Bank> query = entityManger.createQuery("from Bank b where b.username= :username and password= :password",Bank.class);
			query.setParameter("username",username).setParameter("password", password).getSingleResult();
			return true;
			
		}catch(NoResultException ex) {
			
		}
		return false;
	}

	@Override
	public boolean validateAccount(long accountNo) {
		// TODO Auto-generated method stub
		try {
			TypedQuery<Long> query = entityManger.createQuery("select accno from Bank b where b.accno= :accountNo",Long.class);
			query.setParameter("accountNo",accountNo).getSingleResult();
			return true;
		}catch (NoResultException ex) {
			// TODO: handle exception
			return false;
		}
		
	}

	@Transactional
	@Override
	public String applyLoan(long accountNo, Double loanAmount) {
		// TODO Auto-generated method stub
		try {
			
			Query qu = entityManger.createQuery("from Bank where accNo= :accountNo",Bank.class);
			Bank bank=(Bank) qu.setParameter("accountNo", accountNo).getSingleResult();
		    
			Query query = entityManger.createQuery("Update Bank set loanamount= :loanAmount where accno= :accountNo");
			query.setParameter("loanAmount", bank.getLoanamount()+loanAmount).setParameter("accountNo", accountNo).executeUpdate();
			
			entityManger.createNativeQuery("insert into transaction (account_no,message) values (?,?)")
				.setParameter(1, accountNo)
				.setParameter(2, "Apply for loan by "+accountNo+" on "+LocalDate.now())
				.executeUpdate();
				
			return "OK";
			
		}catch(NoResultException ex) {
			return "ERROR";
		}	
	}

	@Override
	public Double showBalance(long accountNo) {
		// TODO Auto-generated method stub
		try {
			TypedQuery<Double> query = entityManger.createQuery("select balance from Bank b where b.accno= :accountNo",Double.class);
			Double balance = query.setParameter("accountNo",accountNo).getSingleResult();
			return balance;
		}catch(NoResultException ex) {
			
		}
		return null;
	}

	@Override
	public Bank getCust(String username, String passward) {
		// TODO Auto-generated method stub
		try {
			TypedQuery<Bank> query = entityManger.createQuery("from Bank b where b.username= :username and password= :password",Bank.class);
			Bank bank = query.setParameter("username",username).setParameter("password", passward).getSingleResult();
			return bank;
		}catch(NoResultException ex) {
			
		}
		return null;
	}

	@Transactional
	@Override
	public String payemi(long accountNo, double emiAmout) {
		// TODO Auto-generated method stub
		try {
			
			Bank bank = entityManger.createQuery("from Bank Where accNo= :accountNo",Bank.class)
					.setParameter("accountNo", accountNo)
					.getSingleResult();
			
			if(bank.getLoanamount()< emiAmout) {
				return "LOW_LOAN";
			}
			
			int result = entityManger.createQuery("Update Bank set loanamount= :loanAmount where accno= :accountNo")
					.setParameter("loanAmount",bank.getLoanamount()-emiAmout)
					.setParameter("accountNo", accountNo)
					.executeUpdate();
			
			if(result>0) {
				entityManger.createNativeQuery("insert into transaction (account_no,message) values (?,?)")
				.setParameter(1, accountNo)
				.setParameter(2, "Pay a EMI "+emiAmout+" by "+accountNo+" on "+LocalDate.now())
				.executeUpdate();
				return "OK";
			}
			
		}catch(NoResultException ex) {
			return "ERROR";
		}
	return "";
	}

	@Transactional
	@Override
	public String deposit(long accountNo, double Amount) {
		// TODO Auto-generated method stub
		try {
			Query query = entityManger.createQuery("from Bank where accNo= :accountNo",Bank.class);
			Bank bank=(Bank) query.setParameter("accountNo", accountNo).getSingleResult();
			
			int result = entityManger.createQuery("Update Bank set balance= :balance where accno= :accountNo")
							.setParameter("balance", Amount+bank.getBalance())
							.setParameter("accountNo", accountNo)
							.executeUpdate();
			if(result>0) {
				entityManger.createNativeQuery("insert into transaction (account_no,message) values (?,?)")
				.setParameter(1, accountNo)
				.setParameter(2, "Deposite "+Amount+" In "+accountNo+" on "+LocalDate.now())
				.executeUpdate();
				return "OK";
			}
		}catch(NoResultException ex) {
			
			return "ERROR";
		}
		return "";
	}

	@Transactional
	@Override
	public String foreClose(long accountNo) {
		// TODO Auto-generated method stub
		try {
			
			Bank bank = entityManger.createQuery("from Bank Where accNo= :accountNo",Bank.class)
					.setParameter("accountNo", accountNo)
					.getSingleResult();
			
			if(bank.getLoanamount()==0l)
				return "NO_LOAN";
			else if(bank.getBalance()>bank.getLoanamount()) {
				
				double newBal = bank.getBalance()-bank.getLoanamount();
				entityManger.createQuery("update Bank set loanamount= :loanAmount,balance= :newBal where accNo= :accountNo")
				.setParameter("loanAmount", 0.0d)
				.setParameter("newBal", newBal)
				.setParameter("accountNo", accountNo)
				.executeUpdate();
				
//				Transaction Detail
				entityManger.createNativeQuery("insert into transaction (account_no,message) values (?,?)")
				.setParameter(1, accountNo)
				.setParameter(2, "ForeClose the Loan of"+accountNo+" on "+LocalDate.now())
				.executeUpdate();
				return "OK";
			}
			else
				return "LOW_BALANCE";
			
		}catch(NoResultException ex) {
			return "ERROR";
		}
	}

	@Override
	public int calEMI(long loanAmount, int period, int rate) {
		// TODO Auto-generated method stub
		return (int) ((loanAmount/period)+ (rate/loanAmount)*100);
	}
	@Transactional
	@Override
	public List<Transaction> printTransaction(long accountNo) {
		
		
	
	
	try {
		TypedQuery<Transaction> query = entityManger.createQuery("from Transaction where account_no= :accountNo",Transaction.class);
		List<Transaction> transaction = query.setParameter("accountNo", accountNo).getResultList();
		return transaction;
	}catch(NoResultException ex) {
		new BankExpception();
	}
	return null;
	
	

}

	@Override
	public boolean validateRegister(String phoneno, String email) {
		// TODO Auto-generated method stub
try {
			
			TypedQuery<Bank> query = entityManger.createQuery("from Bank b where b.phoneno= :phoneno and email= :email",Bank.class);
			query.setParameter("phoneno",phoneno).setParameter("email", email).getSingleResult();
			return true;
			
		}catch(NoResultException ex) {
			return false;
		}

	}

	
	
}