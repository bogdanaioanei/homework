package com.fourfinance.homework.viewModels;

import java.util.List;

import com.fourfinance.homework.dto.LoanDto;

public class LoanHistoryModel {

	private String firstName;
	private String lastName;
	private List<LoanDto> loans;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<LoanDto> getLoans() {
		return loans;
	}

	public void setLoans(List<LoanDto> loans) {
		this.loans = loans;
	}

}
