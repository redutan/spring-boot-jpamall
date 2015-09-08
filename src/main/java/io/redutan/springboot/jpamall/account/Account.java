package io.redutan.springboot.jpamall.account;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * @author redutan
 */
@Entity
@Getter @Setter
public class Account {

	@Id @GeneratedValue
	private Long id;

	@Column(unique = true)
	private String username;

	private String password;

	private String email;

	private String fullName;

	@Temporal(TemporalType.TIMESTAMP)
	private Date joined;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updated;

}
