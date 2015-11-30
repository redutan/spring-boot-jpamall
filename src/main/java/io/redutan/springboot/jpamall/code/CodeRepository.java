package io.redutan.springboot.jpamall.code;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author redutan
 */
@Repository
public interface CodeRepository extends JpaRepository<Code, Long> {
    List<Code> findAllByParentIsNull();

    List<Code> findAllByParent(Code parentCode);

    List<Code> findAllByParentCodeId(Long parentCodeId);
}
