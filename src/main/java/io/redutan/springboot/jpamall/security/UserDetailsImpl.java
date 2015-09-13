package io.redutan.springboot.jpamall.security;

import io.redutan.springboot.jpamall.account.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author redutan
 * @since 2015. 9. 13.
 */
public class UserDetailsImpl extends User {

	public UserDetailsImpl(Account account) {
		super(account.getUsername(), account.getPassword(), authorities(account));
	}

	private static Collection<? extends GrantedAuthority> authorities(Account account) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		if (account.isAdmin()) {
			authorities.add(new SimpleGrantedAuthority(("ROLE_ADMIN")));
		}
		return authorities;
	}
}
