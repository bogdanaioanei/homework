package com.fourfinance.homework.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.fourfinance.homework.base.AbstractMvcTest;

public class UserContextServiceTest extends AbstractMvcTest {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private UserContextService userContextService;

	@Override
	protected String xmlFilePath() {
		return "dbunit/users.xml";
	}
	
	@After
	public void tearDown() throws Exception {
		super.tearDown();
		SecurityContextHolder.clearContext();
	}

	@Test
	public void testGetCurrentUser_findAdmin() {
		UserDetails admin = userDetailsService.loadUserByUsername("admin");
		Authentication authToken = new UsernamePasswordAuthenticationToken(admin.getUsername(), admin.getPassword(),
				admin.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authToken);

		assertTrue(userContextService.isAuthenticated());
		assertNotNull(userContextService.getCurrentUser());
		assertEquals("admin", userContextService.getCurrentUser().getUsername());
	}
	
	@Test
	public void testGetCurrentUser_findUser() {
		UserDetails walt = userDetailsService.loadUserByUsername("heisenberg");
		Authentication authToken = new UsernamePasswordAuthenticationToken(walt.getUsername(), walt.getPassword(),
				walt.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authToken);

		assertTrue(userContextService.isAuthenticated());
		assertNotNull(userContextService.getCurrentUser());
		assertEquals("heisenberg", userContextService.getCurrentUser().getUsername());
	}

	@Test
	public void testGetNoAuthenticatedUser() {
		assertFalse(userContextService.isAuthenticated());
	}
}
