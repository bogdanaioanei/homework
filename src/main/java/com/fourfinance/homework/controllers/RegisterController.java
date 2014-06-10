package com.fourfinance.homework.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fourfinance.homework.formData.UserRegisterForm;
import com.fourfinance.homework.services.UserService;

@Controller
public class RegisterController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String showRegistrationPage(Model model) {
		model.addAttribute("registerModel", new UserRegisterForm());
		return "register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerUser(@ModelAttribute("registerModel") @Valid UserRegisterForm userForm,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "register";
		}

		userService.createNewUser(userForm);

		return "registerSuccess";
	}
}
