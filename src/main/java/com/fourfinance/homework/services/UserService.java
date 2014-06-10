package com.fourfinance.homework.services;

import com.fourfinance.homework.entities.User;
import com.fourfinance.homework.formData.UserRegisterForm;
import com.fourfinance.homework.viewModels.LoanHistoryModel;

public interface UserService {

	User findByUsername(String username);
	
	void createNewUser(UserRegisterForm userForm);
	
	public LoanHistoryModel getLoanHistory();

}
