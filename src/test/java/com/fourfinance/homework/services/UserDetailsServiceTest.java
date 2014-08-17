package com.fourfinance.homework.services;

import com.fourfinance.homework.base.AbstractTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.Assert.assertNotNull;

public class UserDetailsServiceTest extends AbstractTest {

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
