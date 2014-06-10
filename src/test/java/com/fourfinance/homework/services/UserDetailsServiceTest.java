package com.fourfinance.homework.services;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.fourfinance.homework.base.AbstractMvcTest;

public class UserDetailsServiceTest extends AbstractMvcTest {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected String xmlFilePath() {
		return "dbunit/users.xml";
	}
	
	@Test
    public void testLoadUserByUsername() {
        assertNotNull(userDetailsService.loadUserByUsername("admin"));
    }

    @Test(expected = UsernameNotFoundException.class)
    public void testLoadUserByUsername_withError() {
        assertNotNull(userDetailsService.loadUserByUsername("somebody"));
    }

}
