package com.capg.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.lang.NonNull;

@Entity
@Table(name = "Transaction")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NonNull
	@Column(name="accountNo")
	private long accountNo;
	
	@NonNull
	@Column(name="message")
	private String message;
	
	

	public Transaction() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Transaction(long id, long accountNo, String message) {
		super();
		this.id = id;
		this.accountNo = accountNo;
		this.message = message;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(long accountNo) {
		this.accountNo = accountNo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", accountNo=" + accountNo + ", message=" + message + "]";
	}
	
	
}
