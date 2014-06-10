package com.fourfinance.homework.services.impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fourfinance.homework.constants.FourFinanceConstants;
import com.fourfinance.homework.entities.Loan;
import com.fourfinance.homework.entities.LoanRequest;
import com.fourfinance.homework.entities.User;
import com.fourfinance.homework.formData.LoanForm;
import com.fourfinance.homework.repositories.LoanRepository;
import com.fourfinance.homework.services.LoanRequestService;
import com.fourfinance.homework.services.LoanService;
import com.fourfinance.homework.services.UserContextService;

@Service
public class LoanServiceImpl implements LoanService {

	@Autowired
	private UserContextService userContextService;
	
	@Autowired
	private LoanRequestService loanRequestService;

	@Autowired
	private LoanRepository loanRepository;

	@Override
	@Transactional
	public void takeLoan(LoanForm loanForm, String ipAddress) {

		User user = userContextService.getCurrentUser();

		Loan newLoan = new Loan();
		newLoan.setLoanAmount(loanForm.getLoanAmount());
		newLoan.setAmountPaid(Double.valueOf(0));
		newLoan.setAmountLeftToPay(loanForm.getLoanAmount() * getInterestRate());
		newLoan.setExpirationDate(loanForm.getExpirationDate());
		newLoan.setInitialInterestRate(getInterestRate());
		newLoan.setCurrentInterestRate(getInterestRate());
		newLoan.setLoanUID(RandomStringUtils.random(FourFinanceConstants.randomCount, true, true));
		newLoan.setUser(user);

		saveLoan(newLoan);
		
		LoanRequest loanRequest = new LoanRequest();
		loanRequest.setLoan(newLoan);
		loanRequest.setRequestDate(DateTime.now().toDate());
		loanRequest.setIpAddress(ipAddress);
		loanRequestService.saveLoanRequest(loanRequest);
	}

	/**
	 * Normally there should be a much more complex formula here, but I'm just
	 * going to set it to 1.5 for simplicity
	 * */
	private Double getInterestRate() {
		return Double.valueOf(1.5);
	}

	@Override
	public Loan findLoanByUID(String loanUID) {
		return loanRepository.findByLoanUID(loanUID);
	}

	@Override
	public void saveLoan(Loan loan) {
		loanRepository.save(loan);
	}
}
