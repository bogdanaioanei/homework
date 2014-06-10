package com.fourfinance.homework.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fourfinance.homework.base.AbstractDbTest;
import com.fourfinance.homework.entities.User;
import com.fourfinance.homework.repositories.UserRepository;

public class UserTest extends AbstractDbTest {

	@Autowired
	private UserRepository userRepository;

	@Override
	protected String xmlFilePath() {
		return "dbunit/users.xml";
	}

	@Test
	public void testFindByUsername() {
		User walter = userRepository.findByUsername("heisenberg");
		assertNotNull(walter);

		assertEquals("heisenberg", walter.getUsername());
		assertEquals("chemistry", walter.getPassword());
		assertEquals(true, walter.getEnable());
		assertEquals("walt@test.ro", walter.getEmail());
		assertEquals("walter", walter.getFirstName());
		assertEquals("white", walter.getLastName());
	}
}
