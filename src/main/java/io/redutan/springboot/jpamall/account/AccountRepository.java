package io.redutan.springboot.jpamall.account;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author redutan
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
}
