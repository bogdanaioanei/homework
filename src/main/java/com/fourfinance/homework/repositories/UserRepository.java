package com.fourfinance.homework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fourfinance.homework.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	User findByUsername(String username);

}
