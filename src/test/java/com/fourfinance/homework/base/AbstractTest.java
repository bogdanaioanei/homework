package com.fourfinance.homework.base;

import javax.sql.DataSource;

import com.fourfinance.homework.config.TestConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fourfinance.homework.util.DBUtil;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * All our tests will be using DBUnit so we define the setUp and tearDown
 * methods here. Concrete test classes will only need to override the
 * xmlFilePath method to point to the file containing the dataset for the test
 * */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfig.class })
@WebAppConfiguration
public abstract class AbstractTest {

	private DBUtil dbUtil;

	@Autowired
	protected DataSource dataSource;

	protected abstract String xmlFilePath();

	@Before
	public void initDB() {
		dbUtil = new DBUtil(dataSource, xmlFilePath());
	}

	@After
	public void closeDBConnection() {
		dbUtil.close();
	}

}
