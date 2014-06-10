package com.fourfinance.homework.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fourfinance.homework.entities.LoanRequest;

public interface LoanRequestRepository extends JpaRepository<LoanRequest, Long> {

	@Query("SELECT lr FROM LoanRequest lr WHERE lr.ipAddress = ?1 AND lr.requestDate = CURRENT_DATE")
	List<LoanRequest> findByIpAddressAndCurrentDate(String ipAddress);
}
