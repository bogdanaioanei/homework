package com.fourfinance.homework.base;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fourfinance.homework.util.DBUtil;

/**
 * All our tests will be using DBUnit so we define the setUp and tearDown
 * methods here. Concrete test classes will only need to override the
 * xmlFilePath method to point to the file containing the dataset for the test
 * */
@RunWith(value = SpringJUnit4ClassRunner.class)
public abstract class AbstractTest {

	private DBUtil dbUtil;

	@Autowired
	protected DataSource dataSource;

	protected abstract String xmlFilePath();

	@Before
	public void setUp() throws Exception {
		dbUtil = new DBUtil(dataSource, xmlFilePath());
	}

	@After
	public void tearDown() throws Exception {
		dbUtil.close();
	}

}
