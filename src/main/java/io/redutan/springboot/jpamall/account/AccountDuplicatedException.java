package io.redutan.springboot.jpamall.account;

import lombok.Getter;

/**
 * @author redutan
 */
public class AccountDuplicatedException extends RuntimeException {

	@Getter
	private String username;

	public AccountDuplicatedException(String username) {
		this.username = username;
	}
}
