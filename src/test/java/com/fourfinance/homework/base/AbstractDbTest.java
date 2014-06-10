package com.fourfinance.homework.base;

import org.springframework.test.context.ContextConfiguration;

import com.fourfinance.homework.config.DBConfig;

/**
 * The purpose of this class is just to define that we'll be loading only the
 * DBConfig. No need to load the WebContext when testing only entities and JPA
 * Repository beans
 * */
@ContextConfiguration(classes = { DBConfig.class })
public abstract class AbstractDbTest extends AbstractTest {

}
