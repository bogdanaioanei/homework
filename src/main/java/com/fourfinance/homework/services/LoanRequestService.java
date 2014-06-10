package com.fourfinance.homework.services;

import java.util.List;

import com.fourfinance.homework.entities.LoanRequest;

public interface LoanRequestService {

	void saveLoanRequest(LoanRequest loanRequest);

	List<LoanRequest> findByIpAddressAndCurrentDate(String ipAddress);
}
