package com.capg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.capg.Exception.ResourceNotFoundException;
import com.capg.model.Bank;
import com.capg.model.Transaction;
import com.capg.service.BankExpception;
import com.capg.service.LoanService;

@RestController
@RequestMapping("/loan")
public class LoanController {
	
	@Autowired
	LoanService loanService;
	 Bank acc;
	@PostMapping("/create")
	@ResponseBody
	public ResponseEntity<String> createAccount(@RequestBody Bank account) {
		if(account.getAge()<18) {
			return new ResponseEntity<>("Age is Less than 18",HttpStatus.BAD_REQUEST);
		}else {
         
			//if(loanService.createAccount(account)!=null) {
			if((acc=loanService.createAccount(account))!=null) {
				return new ResponseEntity<String>("Account Created with account no= "+acc.getAccno(),HttpStatus.CREATED);
			}else {
				return new ResponseEntity<String>("Cannot Not Create",HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		}	
	}
	
	@GetMapping("/validateUser")
	@ResponseBody
	public ResponseEntity<Object> validate(@RequestParam(name="username") String username,
			@RequestParam(name="password") String password) throws ResourceNotFoundException{
		if(loanService.validateUser(username, password)) {
			return new ResponseEntity<>("true",HttpStatus.OK);
		}else {
			throw new ResourceNotFoundException("Check User Name Password");
		}
	}
	
	@GetMapping("/validateAccount")
	@ResponseBody
	public ResponseEntity<String> validate(@RequestParam(name="accountNo") Long accountNo){
		if(loanService.validateAccount(accountNo)) {
			return new ResponseEntity<String>("true",HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("false",HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/applyLoan")
	@ResponseBody
	public ResponseEntity<String> applyLoan(@RequestParam(name="accountNo") Long accountNo,
			@RequestParam(name="loanAmount") Double loanAmount){
		
		String msg = loanService.applyLoan(accountNo, loanAmount);
		
		if(msg.equals("OK"))
			return new ResponseEntity<String>("Application Submitted",HttpStatus.OK);
		else
			return new ResponseEntity<String>("Application Fail. Check account no",HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/balance")
	@ResponseBody
	public ResponseEntity<String> getBalance(@RequestParam(name="accountNo") Long accountNo){
			
			Double balance =loanService.showBalance(accountNo);
			if(balance != null)
				return new ResponseEntity<String>(String.valueOf(balance),HttpStatus.OK);
			else
				return new ResponseEntity<>("Check account No",HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/getUser")
	@ResponseBody
	public ResponseEntity<Bank> getUser(@RequestParam(name="username") String username,
			@RequestParam(name="password") String password){
		if(loanService.getCust(username, password)!=null) {
			return ResponseEntity.ok(loanService.getCust(username, password));
		}else {
			return new ResponseEntity<>(loanService.getCust(username, password),HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/payEmi")
	@ResponseBody
	public ResponseEntity<String> payEmi(@RequestParam(name="accountNo") Long accountNo,
			@RequestParam(name="emiAmount") Double emiAmount){
		String msg = loanService.payemi(accountNo, emiAmount);
		if(msg.equals("OK"))
			return new ResponseEntity<String>("Payment Sucessful",HttpStatus.OK);
		else if (msg.equals("LOW_LOAN"))
			return new ResponseEntity<>("Cant pay EMI please first apply for the loan",HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<String>("Payment Fail. kindly Check Account No",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PutMapping("/deposit")
	@ResponseBody
	public ResponseEntity<String> deposit(@RequestParam(name="accountNo") Long accountNo,
			@RequestParam(name="amount") Double amount){
		
		String msg = loanService.deposit(accountNo, amount);
		if(msg.equals("OK"))
			return new ResponseEntity<String>("Deposited",HttpStatus.OK);
		else
			return new ResponseEntity<String>("Fail to depisit. kindly check Account No",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PutMapping("/foreClose")
	@ResponseBody
	public ResponseEntity<String> foreClose(@RequestParam(name="accountNo") Long accountNo){
		
		String msg = loanService.foreClose(accountNo);
		
		if(msg.equals("OK"))
			return new ResponseEntity<>("Loan Cleared",HttpStatus.OK);
		else if(msg.equals("NO_LOAN"))
			return new ResponseEntity<String>("Please apply for loan",HttpStatus.BAD_REQUEST);
		else if(msg.equals("LOW_BALANCE"))
			return new ResponseEntity<>("Balance is Low",HttpStatus.BAD_REQUEST);
		else 
			return new ResponseEntity<>("Check account number.",HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/calcEmi")
	@ResponseBody
	public ResponseEntity<String> calcEmi(@RequestParam(name="amount") Long loanAmount,
			@RequestParam(name="period") Integer period, @RequestParam(name="rate") Integer rate){
		int emi = loanService.calEMI(loanAmount, period, rate);
				return new ResponseEntity<String>(String.valueOf(emi),HttpStatus.OK);
	}
	
	@GetMapping("/validateRegister")
	@ResponseBody
	public ResponseEntity<String> validateRegister(@RequestParam(name="phoneno") String phoneno,@RequestParam(name="email") String email){
		if(loanService.validateRegister(phoneno, email)) {
			return new ResponseEntity<String>("true",HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("false",HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/printTransaction")
	@ResponseBody
	public ResponseEntity<List> printTransaction(@RequestParam(name="accountNo") Long accountNo){
		
		List<Transaction> transactionList1=loanService.printTransaction(accountNo);
		return new ResponseEntity<List>(transactionList1,HttpStatus.OK);
		
		
}
	
}