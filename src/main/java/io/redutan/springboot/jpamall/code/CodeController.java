package io.redutan.springboot.jpamall.code;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author myeongju.jung
 */
@RestController
@Slf4j
public class CodeController {

    @Autowired
    CodeRepository repository;

    @RequestMapping(value = "/codes")
    public List<Code> codes() {
        return repository.findAll();
    }

    @RequestMapping(value = "/codes", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Code insert(@Valid @RequestBody CodeDto.Create create) {
        log.info("create = {}", create);
        Code code = new Code();
        BeanUtils.copyProperties(create, code);
        if (create.getParentCodeId() != null) {
            Code parent = repository.findOne(create.getParentCodeId());
            if (parent == null) {
                throw new RuntimeException("not.found.code");
            }
            code.setParentCode(parent);
        }
        return repository.save(code);
    }

    @RequestMapping(value = "/codes", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public Code update(Code code) {
        return repository.save(code);
    }

    @Transactional
    @RequestMapping(value = "/codes/{codeId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable("codeId") Code code) {
        repository.delete(code);
    }
}
