package com.fourfinance.homework.services.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.fourfinance.homework.entities.Authority;
import com.fourfinance.homework.entities.User;
import com.fourfinance.homework.services.UserService;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	private static final boolean ACCOUNT_NON_EXPIRED = true;
	private static final boolean CREDENTIALS_NON_EXPIRE = true;
	private static final boolean ACCOUNT_NON_LOCKED = true;

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userService.findByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException(String.format("No user with username %s found!", username));
		}

		return asUserDetails(user);
	}

	private UserDetails asUserDetails(User user) {

		Collection<Authority> roles = new ArrayList<Authority>();
		roles.add(user.getAuthority());

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				user.getEnable(), ACCOUNT_NON_EXPIRED, CREDENTIALS_NON_EXPIRE, ACCOUNT_NON_LOCKED, roles);

	}

}
