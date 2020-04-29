package com.capg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capg.model.Bank;

@Repository
public interface LoanRepository extends JpaRepository<Bank, Long>, LoanRepositoryCustom{

}
