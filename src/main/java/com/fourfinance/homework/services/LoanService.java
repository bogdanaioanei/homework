package com.fourfinance.homework.services;

import com.fourfinance.homework.entities.Loan;
import com.fourfinance.homework.formData.LoanForm;

public interface LoanService {

	void takeLoan(LoanForm loanForm, String ipAddress);

	Loan findLoanByUID(String loanUID);
	
	void saveLoan(Loan loan);

}
