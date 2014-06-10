package com.fourfinance.homework.services.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.fourfinance.homework.entities.User;
import com.fourfinance.homework.enums.UserRoleEnum;
import com.fourfinance.homework.services.UserContextService;
import com.fourfinance.homework.services.UserService;

@Service
public class UserContextServiceImpl implements UserContextService {

	private User user;

	@Autowired
	private UserService userService;

	@Override
	public User getCurrentUser() {

		if (user != null) {
			return user;
		}

		SecurityContext context = SecurityContextHolder.getContext();
		if (context != null && context.getAuthentication() != null) {
			return userService.findByUsername(context.getAuthentication().getName());
		}

		return null;
	}

	@Override
	public boolean isAuthenticated() {
		SecurityContext context = SecurityContextHolder.getContext();
		if (context != null && context.getAuthentication() != null) {
			Collection<? extends GrantedAuthority> authorities = context.getAuthentication().getAuthorities();

			for (GrantedAuthority grantedAuthority : authorities) {
				String authority = grantedAuthority.getAuthority();

				if (authority.equals(UserRoleEnum.USER.name()) || authority.equals(UserRoleEnum.ADMIN.name())) {
					return true;
				}
			}
		}
		return false;
	}

}
