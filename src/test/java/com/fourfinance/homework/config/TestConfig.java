package com.fourfinance.homework.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * Configuration bean for testing. Normally we would use different DBConfig
 * beans for production and testing but since we're using an embedded database
 * for this homework we'll use the same DBConfig
 * */
@Configuration
@Import({ DBConfig.class, MVCConfig.class, SecurityConfig.class })
@ComponentScan(basePackages = "com.fourfinance.homework.services", excludeFilters = { @ComponentScan.Filter(Configuration.class) })
public class TestConfig {

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages/messages");
		return messageSource;
	}
}
