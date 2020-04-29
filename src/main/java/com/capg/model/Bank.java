package com.capg.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;

@Entity
@Table(name = "Bank")
public class Bank {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long accno;
	
	@NotNull
	@Column(name="name")
	private String name;
	
	@NotNull
	@Column(name="age")
	private int age;
	
	@NotNull
	@Column(name="username")
	private String username;
	
	@NotNull
	@Column(name="password")
	private String password;
	
	@NotNull
	@Column(name="phoneno")
	private String phoneno;
	
	@NotNull
	@Column(name = "balance")
	private double balance;
	
	@NotNull
	@Column(name = "email")
	private String email;
	
	@Column(name="loanamount")
	private double loanamount;
	
	public Bank() {
		
	}

	public Bank(long accno, String name, int age, String username, String password, String phoneno, double balance,
			String email, double loanamount) {
		super();
		this.accno = accno;
		this.name = name;
		this.age = age;
		this.username = username;
		this.password = password;
		this.phoneno = phoneno;
		this.balance = balance;
		this.email = email;
		this.loanamount = loanamount;
	}

	public long getAccno() {
		return accno;
	}

	public void setAccno(long accno) {
		this.accno = accno;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneno() {
		return phoneno;
	}

	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public double getLoanamount() {
		return loanamount;
	}

	public void setLoanamount(double loanamount) {
		this.loanamount = loanamount;
	}

	@Override
	public String toString() {
		return "Bank [accno=" + accno + ", name=" + name + ", age=" + age + ", username=" + username + ", password="
				+ password + ", phoneno=" + phoneno + ", balance=" + balance + ", email=" + email + ", loanamount="
				+ loanamount + "]";
	}
	
}
