package com.fourfinance.homework.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
@Import({ DBConfig.class, MVCConfig.class, SecurityConfig.class })
@ComponentScan(basePackages = "com.fourfinance.homework", excludeFilters = { @ComponentScan.Filter(Configuration.class) })
public class MainConfig {

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages/messages");
		return messageSource;
	}

}
