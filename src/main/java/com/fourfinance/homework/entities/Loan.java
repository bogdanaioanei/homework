package com.fourfinance.homework.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "loans")
public class Loan {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	private Double loanAmount;

	private Double amountPaid;

	private Double amountLeftToPay;

	@Temporal(TemporalType.DATE)
	private Date expirationDate;

	private Double initialInterestRate;

	private Double currentInterestRate;

	@Column(name = "loan_uid")
	private String loanUID;

	@OneToMany(mappedBy = "loan")
	private List<LoanExtension> loanExtensions;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

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

	public List<LoanExtension> getLoanExtensions() {
		return loanExtensions;
	}

	public void setLoanExtensions(List<LoanExtension> loanExtensions) {
		this.loanExtensions = loanExtensions;
	}

}
