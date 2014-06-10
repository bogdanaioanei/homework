package com.fourfinance.homework.dto;

import java.util.Date;

public class LoanExtensionDto {

	private Date newExpirationDate;
	
	private Double interestRateMultiplier;

	private Double newInterestRate;

	private String loanExtensionUID;

	public Date getNewExpirationDate() {
		return newExpirationDate;
	}

	public void setNewExpirationDate(Date newExpirationDate) {
		this.newExpirationDate = newExpirationDate;
	}
	
	public Double getInterestRateMultiplier() {
		return interestRateMultiplier;
	}

	public void setInterestRateMultiplier(Double interestRateMultiplier) {
		this.interestRateMultiplier = interestRateMultiplier;
	}

	public Double getNewInterestRate() {
		return newInterestRate;
	}

	public void setNewInterestRate(Double newInterestRate) {
		this.newInterestRate = newInterestRate;
	}

	public String getLoanExtensionUID() {
		return loanExtensionUID;
	}

	public void setLoanExtensionUID(String loanExtensionUID) {
		this.loanExtensionUID = loanExtensionUID;
	}

}
