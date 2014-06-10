package com.fourfinance.homework.repositories;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fourfinance.homework.base.AbstractDbTest;
import com.fourfinance.homework.constants.FourFinanceConstants;
import com.fourfinance.homework.entities.Loan;
import com.fourfinance.homework.entities.LoanRequest;
import com.fourfinance.homework.entities.User;
import com.fourfinance.homework.repositories.LoanRepository;
import com.fourfinance.homework.repositories.LoanRequestRepository;
import com.fourfinance.homework.repositories.UserRepository;

public class LoanRequestTest extends AbstractDbTest {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private LoanRepository loanRepository;

	@Autowired
	private LoanRequestRepository loanRequestRepository;

	@Override
	protected String xmlFilePath() {
		return "dbunit/loans.xml";
	}

	@Test
	public void testFindByIpAddressAndCurrentDate() {
		
		List<LoanRequest> todaysLoansList = loanRequestRepository.findByIpAddressAndCurrentDate("127.0.0.1");
		assertEquals(0, todaysLoansList.size());
		
		User user = userRepository.findByUsername("admin");
		
		Double loanAmount = Double.valueOf(100);
		Double interestRate = Double.valueOf(1.5);
		
		Loan newLoan = new Loan();
		newLoan.setLoanAmount(loanAmount);
		newLoan.setAmountPaid(Double.valueOf(0));
		newLoan.setAmountLeftToPay(loanAmount * interestRate);
		newLoan.setExpirationDate(DateTime.now().plusWeeks(1).toDate());
		newLoan.setInitialInterestRate(interestRate);
		newLoan.setCurrentInterestRate(interestRate);
		newLoan.setLoanUID(RandomStringUtils.random(FourFinanceConstants.randomCount, true, true));
		newLoan.setUser(user);

		loanRepository.save(newLoan);

		LoanRequest loanRequest = new LoanRequest();
		loanRequest.setLoan(newLoan);
		loanRequest.setRequestDate(DateTime.now().toDate());
		loanRequest.setIpAddress("127.0.0.1");
		loanRequestRepository.save(loanRequest);

		assertEquals(3, loanRequestRepository.findAll().size());
		todaysLoansList = loanRequestRepository.findByIpAddressAndCurrentDate("127.0.0.1");
		assertEquals(1, todaysLoansList.size());
	}
}
