package com.fourfinance.homework.services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;

import com.fourfinance.homework.base.AbstractMvcTest;
import com.fourfinance.homework.formData.LoanForm;

public class LoanRiskAnalysisServiceTest extends AbstractMvcTest {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private LoanService loanService;

	@Autowired
	private LoanRiskAnalysisService loanRiskAnalysisService;

	@Override
	protected String xmlFilePath() {
		return "dbunit/loans.xml";
	}

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@Test
	public void testLoanHasTimeIntervalRisk() {
		LoanForm okLoan = new LoanForm();
		okLoan.setExpirationDate(DateTime.parse("2014-01-02").toDate());
		okLoan.setLoanAmount(Double.valueOf(100));

		String pattern = "yyyy-mm-dd hh.mm.ss aa";

		BindingResult bindingResult = Mockito.mock(BindingResult.class);

		DateTime okDateTime = DateTime.parse("2014-01-02 04.00.00 PM", DateTimeFormat.forPattern(pattern));
		assertFalse(loanRiskAnalysisService.loanHasTimeIntervalRisk(okLoan, bindingResult, okDateTime));

		DateTime badDateTime = DateTime.parse("2014-01-02 03.00.00 AM", DateTimeFormat.forPattern(pattern));
		assertFalse(loanRiskAnalysisService.loanHasTimeIntervalRisk(okLoan, bindingResult, badDateTime));

		DateTime badDateTime2 = DateTime.parse("2014-01-02 06.00.00 AM", DateTimeFormat.forPattern(pattern));
		assertFalse(loanRiskAnalysisService.loanHasTimeIntervalRisk(okLoan, bindingResult, badDateTime2));

		LoanForm badLoan = new LoanForm();
		badLoan.setExpirationDate(DateTime.parse("2014-01-02").toDate());
		badLoan.setLoanAmount(Double.valueOf(999999));

		assertFalse(loanRiskAnalysisService.loanHasTimeIntervalRisk(badLoan, bindingResult, okDateTime));
		assertTrue(loanRiskAnalysisService.loanHasTimeIntervalRisk(badLoan, bindingResult, badDateTime));
		assertTrue(loanRiskAnalysisService.loanHasTimeIntervalRisk(badLoan, bindingResult, badDateTime2));
	}

	@Test
	public void testLoanHasTooManyRequestsRisk() {
		UserDetails admin = userDetailsService.loadUserByUsername("admin");
		Authentication authToken = new UsernamePasswordAuthenticationToken(admin.getUsername(), admin.getPassword(),
				admin.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authToken);

		BindingResult bindingResult = Mockito.mock(BindingResult.class);

		LoanForm loanForm = new LoanForm();
		loanForm.setExpirationDate(DateTime.parse("2014-01-02").toDate());
		loanForm.setLoanAmount(Double.valueOf(100));
		String ipAddress = "127.0.0.1";

		assertFalse(loanRiskAnalysisService.loanHasTooManyRequestsRisk(bindingResult, ipAddress));

		for (int i = 0; i < 3; i++) {
			loanService.takeLoan(loanForm, ipAddress);
		}

		assertTrue(loanRiskAnalysisService.loanHasTooManyRequestsRisk(bindingResult, ipAddress));
	}

}
