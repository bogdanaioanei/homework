package com.fourfinance.homework.services;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.fourfinance.homework.base.AbstractMvcTest;
import com.fourfinance.homework.entities.LoanRequest;
import com.fourfinance.homework.formData.LoanForm;

public class LoanRequestServiceTest extends AbstractMvcTest {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private LoanService loanService;

	@Autowired
	private LoanRequestService loanRequestService;

	@Override
	protected String xmlFilePath() {
		return "dbunit/loans.xml";
	}

	@Override
	@After
	public void tearDown() throws Exception {
		super.tearDown();
		SecurityContextHolder.clearContext();
	}

	@Test
	public void testFindByAddressAndCurrentDate() {
		UserDetails admin = userDetailsService.loadUserByUsername("admin");
		Authentication authToken = new UsernamePasswordAuthenticationToken(admin.getUsername(), admin.getPassword(),
				admin.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authToken);

		LoanForm loanForm = new LoanForm();
		loanForm.setLoanAmount(Double.valueOf(100));
		loanForm.setExpirationDate(DateTime.now().toDate());
		String ipAddress = "192.168.20.70";

		loanService.takeLoan(loanForm, ipAddress);

		List<LoanRequest> loansOfToday = loanRequestService.findByIpAddressAndCurrentDate(ipAddress);

		assertEquals(1, loansOfToday.size());
	}

}
