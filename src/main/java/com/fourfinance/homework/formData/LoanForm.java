package com.fourfinance.homework.formData;

import java.util.Date;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;


public class LoanForm {

	@NotNull
	@Min(value = 1)
	@Digits(fraction = 2, integer = 100)
	private Double loanAmount;

	@NotNull
	@DateTimeFormat(iso=ISO.DATE)
	@Future
	private Date expirationDate;

	public Double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
}
