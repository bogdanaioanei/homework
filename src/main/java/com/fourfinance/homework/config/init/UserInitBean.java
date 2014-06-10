package com.fourfinance.homework.config.init;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fourfinance.homework.formData.UserRegisterForm;
import com.fourfinance.homework.services.UserService;

@Component
public class UserInitBean implements InitializingBean {

	@Autowired
	private UserService userService;

	@Override
	public void afterPropertiesSet() throws Exception {

		UserRegisterForm userAdmin = new UserRegisterForm();
		userAdmin.setFirstName("Bogdan");
		userAdmin.setLastName("Aioanei");
		userAdmin.setEmail("bogdan.aioanei@yahoo.com");
		userAdmin.setPassword("admin");
		userAdmin.setUsername("admin");
		userAdmin.setRole("ADMIN");

		userService.createNewUser(userAdmin);
	}

}
