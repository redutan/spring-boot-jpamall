package io.redutan.springboot.jpamall.account;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author redutan
 * @since 2015. 9. 11.
 */
@Data
@NoArgsConstructor
public class AccountNotFoundException extends RuntimeException {
	private Long id;

	public AccountNotFoundException(Long id){
		this.id = id;
	}
}
