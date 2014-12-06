package com.fourfinance.homework.controllers;

import com.fourfinance.homework.base.AbstractMvcTest;
import com.fourfinance.homework.constants.FourFinanceConstants;
import com.fourfinance.homework.formData.LoanForm;
import com.fourfinance.homework.services.LoanService;
import com.fourfinance.homework.services.mock.CurrentDateMock;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.joda.time.DateTime.now;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class HomeLoanControllerTest extends AbstractMvcTest {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private LoanService loanService;

	@Autowired
	private CurrentDateMock currentDate;

	@Override
	protected String xmlFilePath() {
		return "dbunit/loans.xml";
	}

	@After
	public void tearDown() {
		SecurityContextHolder.clearContext();
	}

	@Test
    @Ignore
	public void testGetHomePage_notAuthenticated() throws Exception {
		mockMvc.perform(get("/").header("Accept", "*/*")).andExpect(redirectedUrl("/login"))
				.andExpect(view().name("redirect:/login")).andExpect(status().isFound());
	}

	@Test
	public void testGetHomePage_authenticated() throws Exception {
		setAdminAsLoggedInUser();
		mockMvc.perform(get("/").header("Accept", "*/*")).andExpect(view().name("home"))
				.andExpect(model().attributeExists("loanModel")).andExpect(status().isOk());
	}

	@Test
    @Ignore
	public void testGetLoanHistoryPage_notAuthenticated() throws Exception {
		mockMvc.perform(get("/loan/history").header("Accept", "*/*")).andExpect(redirectedUrl("/login"))
				.andExpect(view().name("redirect:/login")).andExpect(status().isFound());
	}

	@Test
	public void testGetLoanHistoryPage_authenticated() throws Exception {
		setAdminAsLoggedInUser();
		mockMvc.perform(get("/loan/history").header("Accept", "*/*")).andExpect(view().name("loan/history"))
				.andExpect(model().attributeExists("loanHistory")).andExpect(status().isOk());
	}

	@Test
	public void testRequestLoan_notAuthenticated() throws Exception {
		mockMvc.perform(
                post("/loan/request").header("Accept", "*/*").param("loanAmount", "100")
                        .param("expirationDate", getFutureDateString())).andExpect(status().isFound());
	}

	@Test
	public void testRequestLoan_formHasErrors() throws Exception {
		setAdminAsLoggedInUser();
		mockMvc.perform(
				post("/loan/request").header("Accept", "*/*").param("loanAmount", "xxx")
						.param("expirationDate", "2002-12-31"))
				.andExpect(model().attributeHasFieldErrors("loanModel", "loanAmount", "expirationDate"))
				.andExpect(view().name("home")).andExpect(status().isOk());
	}

	@Test
	public void testRequestLoan_withTooManyRequestsRisks() throws Exception {
		setAdminAsLoggedInUser();

		LoanForm loanForm = new LoanForm();
		loanForm.setExpirationDate(DateTime.parse(getFutureDateString()).toDate());
		loanForm.setLoanAmount(Double.valueOf(100));
		String ipAddress = "127.0.0.1";

		for (int i = 0; i < 3; i++) {
			loanService.takeLoan(loanForm, ipAddress);
		}

		mockMvc.perform(
				post("/loan/request").header("Accept", "*/*").param("loanAmount", "100")
						.param("expirationDate", getFutureDateString()))
				.andExpect(model().attributeHasFieldErrors("loanModel", "loan")).andExpect(view().name("home"))
				.andExpect(status().isOk());
	}

	@Test
	public void testRequestLoan_withTimeIntervalRisks() throws Exception {
		setAdminAsLoggedInUser();

		DateTime now = now();
		DateTime invalidDateTime = new DateTime(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth(), 4, 30, 0);

		CurrentDateMock mockDate = currentDate;
		mockDate.setCurrentTime(invalidDateTime);

		mockMvc.perform(
				post("/loan/request").header("Accept", "*/*")
						.param("loanAmount", FourFinanceConstants.loanMaxPossibleAmount.toString())
						.param("expirationDate", getFutureDateString()))
				.andExpect(model().attributeHasFieldErrors("loanModel", "loan")).andExpect(view().name("home"))
				.andExpect(status().isOk());
	}

	@Test
	public void testRequestLoan_ok() throws Exception {
		setAdminAsLoggedInUser();

		mockMvc.perform(
				post("/loan/request").header("Accept", "*/*").param("loanAmount", "100")
						.param("expirationDate", getFutureDateString()))
				.andExpect(view().name("redirect:/loan/history")).andExpect(status().isFound());
	}

	@Test
	public void testExtendLoan_notAuthenticated() throws Exception {
		mockMvc.perform(post("/loan/extend").header("Accept", "*/*").param("loanUID", "ABC"))
                .andExpect(status().isFound());
	}

	@Test
	public void testExtendLoan_loanUIDInvalid() throws Exception {
		setAdminAsLoggedInUser();
		mockMvc.perform(post("/loan/extend").header("Accept", "*/*").param("loanUID", "invalid"))
				.andExpect(view().name("loan/history")).andExpect(status().isForbidden());
	}

	@Test
	public void testExtendLoan_ok() throws Exception {
		setAdminAsLoggedInUser();

		mockMvc.perform(post("/loan/extend").header("Accept", "*/*").param("loanUID", "ABC"))
				.andExpect(view().name("redirect:/loan/history")).andExpect(status().isFound());
	}

	private String getFutureDateString() {
		DateTime futureDate = now().plusYears(1);

        return futureDate.getYear() + "-" + futureDate.getMonthOfYear() + "-" + futureDate.getDayOfMonth();
	}

	private void setAdminAsLoggedInUser() {
		UserDetails admin = userDetailsService.loadUserByUsername("admin");
		Authentication authToken = new UsernamePasswordAuthenticationToken(admin.getUsername(), admin.getPassword(),
				admin.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authToken);
	}

}
