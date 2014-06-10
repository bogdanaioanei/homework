package com.fourfinance.homework.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fourfinance.homework.base.AbstractDbTest;
import com.fourfinance.homework.entities.Loan;
import com.fourfinance.homework.repositories.LoanRepository;

public class LoanTest extends AbstractDbTest {

	@Autowired
	private LoanRepository loanRepository;

	@Override
	protected String xmlFilePath() {
		return "dbunit/loans.xml";
	}

	@Test
	public void testFindByLoanUID() {
		Loan loan = loanRepository.findByLoanUID("ABC");
		assertNotNull(loan);
		assertEquals(Double.valueOf(100), loan.getLoanAmount());
		assertEquals(Double.valueOf(0), loan.getAmountPaid());
		assertEquals(Double.valueOf(150), loan.getAmountLeftToPay());
		assertEquals(DateTime.parse("2013-07-27").toDate(), loan.getExpirationDate());
		assertEquals(Double.valueOf(1.5), loan.getInitialInterestRate());
		assertEquals(Double.valueOf(1.5), loan.getCurrentInterestRate());
		assertEquals("ABC", loan.getLoanUID());
	}
}
