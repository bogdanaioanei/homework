package com.fourfinance.homework.services;

import com.fourfinance.homework.base.AbstractTest;
import com.fourfinance.homework.entities.Loan;
import com.fourfinance.homework.formData.LoanForm;
import com.fourfinance.homework.repositories.LoanRepository;
import com.fourfinance.homework.repositories.LoanRequestRepository;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class LoanServiceTest extends AbstractTest {

	@Autowired
	private LoanService loanService;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private LoanRepository loanRepository;

	@Autowired
	private LoanRequestRepository loanRequestRepository;

	@Override
	protected String xmlFilePath() {
		return "dbunit/loans.xml";
	}

	@After
	public void tearDown() {
		SecurityContextHolder.clearContext();
	}

	@Test
	public void testTakeLoan() {

		assertEquals(2, loanRepository.findAll().size());
		assertEquals(2, loanRequestRepository.findAll().size());

		UserDetails admin = userDetailsService.loadUserByUsername("admin");
		Authentication authToken = new UsernamePasswordAuthenticationToken(admin.getUsername(), admin.getPassword(),
				admin.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authToken);

		LoanForm loanForm = new LoanForm();
		loanForm.setLoanAmount(Double.valueOf(100));
		loanForm.setExpirationDate(DateTime.now().toDate());
		String ipAddress = "127.0.0.1";

		loanService.takeLoan(loanForm, ipAddress);

		assertEquals(3, loanRepository.findAll().size());
		assertEquals(3, loanRequestRepository.findAll().size());
	}

	@Test
	public void testFindLoanByUID() {
		Loan loan = loanService.findLoanByUID("ABC");
		
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
