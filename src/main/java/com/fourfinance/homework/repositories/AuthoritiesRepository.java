package com.fourfinance.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fourfinance.homework.entities.Authority;

public interface AuthoritiesRepository extends JpaRepository<Authority, Integer>{

}
