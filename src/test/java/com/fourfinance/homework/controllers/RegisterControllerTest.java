package com.fourfinance.homework.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;

import com.fourfinance.homework.base.AbstractMvcTest;

public class RegisterControllerTest extends AbstractMvcTest {

	@Override
	protected String xmlFilePath() {
		return "dbunit/users.xml";
	}

	@Test
	public void testGetRegisterPage() throws Exception {
		mockMvc.perform(get("/register").header("Accept", "*/*")).andExpect(view().name("register"))
				.andExpect(model().attributeExists("registerModel")).andExpect(status().isOk());
	}

	@Test
	public void testPostRegisterUser_withErrors() throws Exception {
		mockMvc.perform(post("/register").header("Accept", "*/*")).andExpect(view().name("register"))
				.andExpect(status().isOk());
	}

	@Test
	public void testPostRegisterUser_ok() throws Exception {

		mockMvc.perform(
				post("/register").header("Accept", "*/*").param("firstName", "Bogdan").param("lastName", "Aioanei")
						.param("username", "baioanei").param("password", "secret").param("email", "bogdan@test.com")
						.param("role", "USER")).andExpect(view().name("registerSuccess")).andExpect(status().isOk());
	}

}
