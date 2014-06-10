package com.fourfinance.homework.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fourfinance.homework.entities.LoanRequest;
import com.fourfinance.homework.repositories.LoanRequestRepository;
import com.fourfinance.homework.services.LoanRequestService;

@Service
public class LoanRequestServiceImpl implements LoanRequestService {

	@Autowired
	private LoanRequestRepository loanRequestRepository;

	@Override
	public void saveLoanRequest(LoanRequest loanRequest) {
		loanRequestRepository.save(loanRequest);
	}

	@Override
	public List<LoanRequest> findByIpAddressAndCurrentDate(String ipAddress) {
		return loanRequestRepository.findByIpAddressAndCurrentDate(ipAddress);
	}
}
