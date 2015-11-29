package io.redutan.springboot.jpamall.code;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author redutan
 * @since 2015. 11. 30.
 */
@Component
public class CodeFactory {
    @Autowired
    private CodeRepository repository;

    public Code getObject(CodeDto.Create create) {
        Code code = new Code();
        BeanUtils.copyProperties(create, code);
        if (create.getParentCodeId() != null) {
            Code parent = repository.findOne(create.getParentCodeId());
            if (parent == null) {
                throw new RuntimeException("not.found.code");
            }
            code.setParent(parent);
        }
        return code;
    }
}
