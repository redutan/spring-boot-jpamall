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

    @Autowired
    CodeFactory factory;

    @RequestMapping(value = "/codes", method = RequestMethod.GET)
    public List<Code> codes() {
        return repository.findAll();
    }

    @RequestMapping(value = "/codes/{codeId}", method = RequestMethod.GET)

    public Code code(@PathVariable("codeId") Code code) {
        return code;
    }

    @Transactional
    @RequestMapping(value = "/codes", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Code insert(@Valid @RequestBody CodeDto.Create create) {
        Code code = factory.getObject(create);
        return repository.save(code);
    }

    @Transactional
    @RequestMapping(value = "/codes/{codeId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public Code update(@PathVariable("codeId") Code code,
                       @Valid @RequestBody CodeDto.Update update) {
        BeanUtils.copyProperties(update, code);
        return repository.save(code);
    }

    @Transactional
    @RequestMapping(value = "/codes/{codeId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable("codeId") Code code) {
        if (code == null) {
            // 존재하지 않는 코드입니다.
            throw new RuntimeException("not.exist.code");
        }
        repository.delete(code);
    }
}
