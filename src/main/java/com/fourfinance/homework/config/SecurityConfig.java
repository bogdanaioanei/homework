package com.fourfinance.homework.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.dao.SystemWideSaltSource;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String SYSTEM_WIDE_SALT = "thsi238e0dsaS0jjAkjk93LjshT";

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/", "/home").authenticated().antMatchers("/resources/**","/register").permitAll();
		http.formLogin().loginProcessingUrl("/j_spring_security_check").defaultSuccessUrl("/").loginPage("/login")
				.permitAll().and().logout().permitAll();

		/*
		 * Normally we would not disable CSRF protection. But for the sake of
		 * simplicity we'll leave it disabled as nobody will try to hack this
		 * homework :)
		 */
		http.csrf().disable();
	}

	@Bean
	public ShaPasswordEncoder passwordEncoder() {
		return new ShaPasswordEncoder();
	}

	@Bean
	public SystemWideSaltSource saltSource() {
		SystemWideSaltSource saltSource = new SystemWideSaltSource();
		saltSource.setSystemWideSalt(SYSTEM_WIDE_SALT);

		return saltSource;
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

		daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
		daoAuthenticationProvider.setSaltSource(saltSource());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);

		return daoAuthenticationProvider;
	}

}
