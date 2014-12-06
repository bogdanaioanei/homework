package com.fourfinance.homework.base;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fourfinance.homework.config.TestConfig;

/**
 * Concrete test classes will extend this class when loading the WebContext is
 * necessary
 * */
public abstract class AbstractMvcTest extends AbstractTest {

	@Autowired
	protected WebApplicationContext wac;
	
	protected MockMvc mockMvc;

    @Before
    public void initMockMVC() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
}
