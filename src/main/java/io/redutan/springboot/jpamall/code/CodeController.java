package io.redutan.springboot.jpamall.code;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    CodeService codeService;

    @RequestMapping(value = "/codes", method = RequestMethod.GET)
    public List<Code> codes(@RequestParam(value = "parentCodeId", required = false) Long parentCodeId) {
        return codeService.getCodes(parentCodeId);
    }

    @RequestMapping(value = "/codes/{codeId}", method = RequestMethod.GET)
    public Code code(@PathVariable Long codeId) {
        return codeService.getCode(codeId);
    }

    @RequestMapping(value = "/codes", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Code insert(@Valid @RequestBody CodeDto.Create create) {
        return codeService.create(create);
    }

    @RequestMapping(value = "/codes/{codeId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public Code update(@PathVariable Long codeId,
                       @Valid @RequestBody CodeDto.Update update) {
        return codeService.update(codeId, update);
    }

    @RequestMapping(value = "/codes/{codeId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long codeId) {
        codeService.delete(codeId);
    }
}
