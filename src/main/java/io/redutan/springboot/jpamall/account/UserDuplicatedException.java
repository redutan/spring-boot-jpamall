package io.redutan.springboot.jpamall.account;

import lombok.Getter;

/**
 * @author redutan
 */
public class UserDuplicatedException extends RuntimeException {

	@Getter
	private String username;

	public UserDuplicatedException(String username) {
		this.username = username;
	}
}
