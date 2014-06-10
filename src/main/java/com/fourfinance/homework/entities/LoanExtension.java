package com.fourfinance.homework.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "loan_extensions")
public class LoanExtension {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	@JoinColumn(name = "loan_id")
	private Loan loan;

	@Temporal(TemporalType.DATE)
	private Date newExpirationDate;

	private Double interestRateMultiplier;

	private Double newInterestRate;

	@Column(name = "loan_extension_uid")
	private String loanExtensionUID;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
	}

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
