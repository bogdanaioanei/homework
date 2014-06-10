package com.fourfinance.homework.services;

import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.SystemWideSaltSource;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.fourfinance.homework.base.AbstractMvcTest;
import com.fourfinance.homework.dto.LoanDto;
import com.fourfinance.homework.dto.LoanExtensionDto;
import com.fourfinance.homework.entities.User;
import com.fourfinance.homework.enums.UserRoleEnum;
import com.fourfinance.homework.formData.UserRegisterForm;
import com.fourfinance.homework.repositories.UserRepository;
import com.fourfinance.homework.viewModels.LoanHistoryModel;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

public class UserServiceTest extends AbstractMvcTest {

	@Autowired
	private UserService userService;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SystemWideSaltSource systemWideSaltSource;

	@Autowired
	private ShaPasswordEncoder shaPasswordEncoder;

	@Override
	protected String xmlFilePath() {
		return "dbunit/loans.xml";
	}

	@Test
	public void testFindByUsername() {
		assertNotNull(userService.findByUsername("admin"));
	}

	@Test
	public void testCreateNewUser() {
		UserRegisterForm userForm = new UserRegisterForm();
		userForm.setUsername("newUser");
		userForm.setPassword("secret");
		userForm.setEmail("someone@new.com");
		userForm.setRole(UserRoleEnum.USER.name());
		userForm.setFirstName("first");
		userForm.setLastName("last");

		userService.createNewUser(userForm);

		assertEquals(3, userRepository.findAll().size());

		User newUser = userService.findByUsername("newUser");
		assertNotNull(newUser);
		assertEquals("newUser", newUser.getUsername());
		String encodedPassword = shaPasswordEncoder.encodePassword("secret", systemWideSaltSource.getSystemWideSalt());
		assertEquals(encodedPassword, newUser.getPassword());
		assertEquals("someone@new.com", newUser.getEmail());
		assertEquals(UserRoleEnum.USER, newUser.getAuthority().getRole());
		assertEquals("first", newUser.getFirstName());
		assertEquals("last", newUser.getLastName());
	}

	@Test
	public void testGetLoanHistory() {
		UserDetails admin = userDetailsService.loadUserByUsername("heisenberg");
		Authentication authToken = new UsernamePasswordAuthenticationToken(admin.getUsername(), admin.getPassword(),
				admin.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authToken);

		LoanHistoryModel loanHistory = userService.getLoanHistory();
		assertNotNull(loanHistory);
		assertEquals(1, loanHistory.getLoans().size());
		LoanDto loan = loanHistory.getLoans().get(0);
		assertEquals(Double.valueOf(100), loan.getLoanAmount());
		assertEquals(Double.valueOf(0), loan.getAmountPaid());
		assertEquals(Double.valueOf(225), loan.getAmountLeftToPay());
		assertEquals(DateTime.parse("2013-07-27").toDate(), loan.getExpirationDate());
		assertEquals(Double.valueOf(1.5), loan.getInitialInterestRate());
		assertEquals(Double.valueOf(2.25), loan.getCurrentInterestRate());
		assertEquals("XYZ", loan.getLoanUID());

		assertEquals(1, loan.getLoanExtensions().size());
		LoanExtensionDto loanExtension = loan.getLoanExtensions().get(0);
		assertEquals(DateTime.parse("2013-08-04").toDate(), loanExtension.getNewExpirationDate());
		assertEquals(Double.valueOf(1.5), loanExtension.getInterestRateMultiplier());
		assertEquals(Double.valueOf(2.25), loanExtension.getNewInterestRate());
		assertEquals("extXYZ", loanExtension.getLoanExtensionUID());
	}

}
