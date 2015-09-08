package io.redutan.springboot.jpamall.account;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * @author redutan
 */
public class AccountTest {

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void testGetAndSetAll() throws Exception {
		Account account = new Account();
		account.setUsername("redutan");
		account.setPassword("p@ssw0rd");

		assertThat(account.getUsername(), is("redutan"));
	}
}