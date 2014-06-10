package com.fourfinance.homework.services;

import org.joda.time.DateTime;
import org.springframework.validation.BindingResult;

import com.fourfinance.homework.formData.LoanForm;

public interface LoanRiskAnalysisService {

	boolean loanHasTimeIntervalRisk(LoanForm loanForm, BindingResult bindingResult, DateTime dateTime);
	
	boolean loanHasTooManyRequestsRisk(BindingResult bindingResult, String ipAddress);
}
