package com.fourfinance.homework.dto;

import java.util.Date;
import java.util.List;

public class LoanDto {

	private Double loanAmount;
	
	private Double amountPaid;

	private Double amountLeftToPay;

	private Date expirationDate;

	private Double initialInterestRate;
	
	private Double currentInterestRate;

	private String loanUID;

	private List<LoanExtensionDto> loanExtensions;

	public Double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public Double getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(Double amountPaid) {
		this.amountPaid = amountPaid;
	}

	public Double getAmountLeftToPay() {
		return amountLeftToPay;
	}

	public void setAmountLeftToPay(Double amountLeftToPay) {
		this.amountLeftToPay = amountLeftToPay;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Double getInitialInterestRate() {
		return initialInterestRate;
	}

	public void setInitialInterestRate(Double initialInterestRate) {
		this.initialInterestRate = initialInterestRate;
	}

	public Double getCurrentInterestRate() {
		return currentInterestRate;
	}

	public void setCurrentInterestRate(Double currentInterestRate) {
		this.currentInterestRate = currentInterestRate;
	}

	public String getLoanUID() {
		return loanUID;
	}

	public void setLoanUID(String loanUID) {
		this.loanUID = loanUID;
	}

	public List<LoanExtensionDto> getLoanExtensions() {
		return loanExtensions;
	}

	public void setLoanExtensions(List<LoanExtensionDto> loanExtensions) {
		this.loanExtensions = loanExtensions;
	}
}
