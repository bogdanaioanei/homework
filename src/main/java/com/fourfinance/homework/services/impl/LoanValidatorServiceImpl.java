package com.fourfinance.homework.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fourfinance.homework.entities.Loan;
import com.fourfinance.homework.services.LoanService;
import com.fourfinance.homework.services.LoanValidatorService;

@Service
public class LoanValidatorServiceImpl implements LoanValidatorService {

	@Autowired
	private LoanService loanService;

	@Override
	public boolean loanExists(String loanUID) {
		Loan loan = loanService.findLoanByUID(loanUID);
		if (loan != null) {
			return true;
		}

		return false;
	}

}
