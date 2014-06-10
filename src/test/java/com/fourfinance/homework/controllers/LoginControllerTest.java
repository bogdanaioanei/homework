package com.fourfinance.homework.controllers;

import org.junit.Test;

import com.fourfinance.homework.base.AbstractMvcTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class LoginControllerTest extends AbstractMvcTest {

	@Override
	protected String xmlFilePath() {
		return "dbunit/users.xml";
	}

	@Test
	public void testGetLoginPage() throws Exception {
		mockMvc.perform(get("/login").header("Accept", "*/*")).andExpect(status().isOk())
				.andExpect(view().name("login"));
	}
}
