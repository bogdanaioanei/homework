package com.fourfinance.homework.services.impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fourfinance.homework.constants.FourFinanceConstants;
import com.fourfinance.homework.entities.Loan;
import com.fourfinance.homework.entities.LoanExtension;
import com.fourfinance.homework.repositories.LoanExtensionRepository;
import com.fourfinance.homework.services.LoanExtensionService;
import com.fourfinance.homework.services.LoanService;

@Service
public class LoanExtensionServiceImpl implements LoanExtensionService {

	@Autowired
	private LoanService loanService;

	@Autowired
	private LoanExtensionRepository loanExtensionRepository;

	@Override
	@Transactional
	public void extendLoan(String loanUID) {
		Loan loan = loanService.findLoanByUID(loanUID);

		LoanExtension loanExtension = new LoanExtension();
		loanExtension.setLoan(loan);

		/*Save details of loan extension*/
		DateTime newDateTime = DateTime.parse(loan.getExpirationDate().toString());
		loanExtension.setNewExpirationDate(newDateTime.plusWeeks(1).toDate());
		loanExtension.setInterestRateMultiplier(interestRateMultiplier());
		loanExtension.setNewInterestRate(loan.getCurrentInterestRate() * loanExtension.getInterestRateMultiplier());
		loanExtension.setLoanExtensionUID(RandomStringUtils.random(FourFinanceConstants.randomCount, true, true));
		loanExtensionRepository.save(loanExtension);
		
		/*Update loan data with new values*/
		loan.setExpirationDate(loanExtension.getNewExpirationDate());
		loan.setCurrentInterestRate(loanExtension.getNewInterestRate());
		loan.setAmountLeftToPay(loan.getAmountLeftToPay() * loanExtension.getInterestRateMultiplier());
		loanService.saveLoan(loan);
	}

	private Double interestRateMultiplier() {
		return new Double(1.5);
	}

}
