package com.fourfinance.homework.services;

import com.fourfinance.homework.entities.User;

public interface UserContextService {

	public User getCurrentUser();

	public boolean isAuthenticated();

}
