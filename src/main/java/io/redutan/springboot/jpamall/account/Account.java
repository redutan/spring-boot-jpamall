package io.redutan.springboot.jpamall.account;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author redutan
 */
@Entity
@Getter @Setter
public class Account {

	@Id
	@GeneratedValue
	private Long id;

	private String loginId;

	private String password;
}
