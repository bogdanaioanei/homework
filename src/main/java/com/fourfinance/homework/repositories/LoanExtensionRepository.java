package com.fourfinance.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fourfinance.homework.entities.LoanExtension;

public interface LoanExtensionRepository extends JpaRepository<LoanExtension, Long> {

}
