package io.redutan.springboot.jpamall.security;

import io.redutan.springboot.jpamall.account.Account;
import io.redutan.springboot.jpamall.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author redutan
 * @since 2015. 9. 13.
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	AccountRepository accountRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accountRepository.findByUsername(username);
		if (account == null) {
			throw new UsernameNotFoundException(username);
		}
		return new UserDetailsImpl(account);
	}
}
