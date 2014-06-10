package com.fourfinance.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fourfinance.homework.entities.Loan;

public interface LoanRepository extends JpaRepository<Loan, Long> {

	Loan findByLoanUID(String loanUID);
}
