package com.fourfinance.homework.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import com.fourfinance.homework.base.AbstractTest;
import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fourfinance.homework.base.AbstractMvcTest;
import com.fourfinance.homework.entities.Loan;
import com.fourfinance.homework.entities.LoanExtension;
import com.fourfinance.homework.repositories.LoanExtensionRepository;

public class LoanExtensionServiceTest extends AbstractTest {

	@Autowired
	private LoanService loanService;

	@Autowired
	private LoanExtensionService loanExtensionService;

	@Autowired
	private LoanExtensionRepository loanExtensionRepository;

	@Override
	protected String xmlFilePath() {
		return "dbunit/loans.xml";
	}

	@Test
	public void testExtendLoan() {
		String loanUID = "ABC";
		
		Loan loan = loanService.findLoanByUID(loanUID);
		
		Date expirationDate = loan.getExpirationDate();
		Double amountLeftToPay = loan.getAmountLeftToPay();
		Double currentInterestRate = loan.getCurrentInterestRate();
		
		loanExtensionService.extendLoan(loanUID);

		assertEquals(2, loanExtensionRepository.findAll().size());
		
		loan = loanService.findLoanByUID(loanUID);

		LoanExtension loanExtension = loanExtensionRepository.findOne(loan.getId());
		assertNotNull(loanExtension);
		assertEquals(new DateTime(expirationDate.getTime()).plusWeeks(1).toDate(), loan.getExpirationDate());
		Double newAmountLeftToPay = amountLeftToPay * loanExtension.getInterestRateMultiplier();
		assertEquals(newAmountLeftToPay, loan.getAmountLeftToPay());
		Double newInterestRate = currentInterestRate * loanExtension.getInterestRateMultiplier();
		assertEquals(newInterestRate, loan.getCurrentInterestRate());
		
	}

}
