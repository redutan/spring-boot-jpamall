package io.redutan.springboot.jpamall.code;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author redutan
 */
@Repository
public interface CodeRepository extends JpaRepository<Code, Long> {
}
