package com.fourfinance.homework.services;

import com.fourfinance.homework.base.AbstractTest;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.junit.Assert.*;

public class UserContextServiceTest extends AbstractTest {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private UserContextService userContextService;

	@Override
	protected String xmlFilePath() {
		return "dbunit/users.xml";
	}
	
	@After
	public void tearDown() {
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
