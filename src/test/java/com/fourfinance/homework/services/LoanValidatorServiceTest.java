package com.fourfinance.homework.services;

import com.fourfinance.homework.base.AbstractTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fourfinance.homework.base.AbstractMvcTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LoanValidatorServiceTest extends AbstractTest {

	@Autowired
	private LoanValidatorService loanValidatorService;
	
	@Override
	protected String xmlFilePath() {
		return "dbunit/loans.xml";
	}

	@Test
	public void testLoanExists(){
		assertTrue(loanValidatorService.loanExists("ABC"));
		assertFalse(loanValidatorService.loanExists("invalid"));
	}
}
