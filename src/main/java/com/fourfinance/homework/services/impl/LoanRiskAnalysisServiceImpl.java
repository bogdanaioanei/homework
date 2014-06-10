package com.fourfinance.homework.services.impl;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.fourfinance.homework.constants.FourFinanceConstants;
import com.fourfinance.homework.entities.LoanRequest;
import com.fourfinance.homework.formData.LoanForm;
import com.fourfinance.homework.services.LoanRequestService;
import com.fourfinance.homework.services.LoanRiskAnalysisService;

@Service
public class LoanRiskAnalysisServiceImpl implements LoanRiskAnalysisService {

	@Autowired
	private LoanRequestService loanRequestService;

	/**
	 * If the loan is made between 00:00 and 06:00
	 */
	@Override
	public boolean loanHasTimeIntervalRisk(LoanForm loanForm, BindingResult bindingResult, DateTime dateTime) {

		boolean timeIntervalCondition = (dateTime.getHourOfDay() >= 0 && dateTime.getHourOfDay() < 6)
				|| (dateTime.getHourOfDay() == 6 && dateTime.getMinuteOfHour() == 0);

		boolean maxAmountCondition = loanForm.getLoanAmount() >= FourFinanceConstants.loanMaxPossibleAmount;

		if (timeIntervalCondition && maxAmountCondition) {
			bindingResult.addError(new FieldError("loan", "loan",
					"Loans cannot be granted between 00:00 and 06:00 hours with this amount."));
			return true;
		}

		return false;
	}

	/**
	 * If there were 3 requests from the same IP today
	 * */
	@Override
	public boolean loanHasTooManyRequestsRisk(BindingResult bindingResult, String ipAddress) {
		List<LoanRequest> loanRequests = loanRequestService.findByIpAddressAndCurrentDate(ipAddress);

		if (loanRequests.size() < 3) {
			return false;
		}

		bindingResult.addError(new FieldError("loan", "loan",
				"More than 3 loans have been granted today. Try again tomorrow"));
		return true;
	}

}
