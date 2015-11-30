package io.redutan.springboot.jpamall.code;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author myeongju.jung
 */
@Service
public class CodeService {
    @Autowired
    private CodeRepository codeRepository;

    @Autowired
    private CodeFactory factory;

    public List<Code> getCodes(Long parentCodeId) {
        if (parentCodeId == null || parentCodeId == 1L) {
            return codeRepository.findAllByParentIsNull();
        } else {
            return codeRepository.findAllByParentCodeId(parentCodeId);
        }
    }

    public Code getCode(Long codeId) {
        return tryGetCode(codeId);
    }

    @Transactional
    public Code create(CodeDto.Create create) {
        Code code = factory.getObject(create);
        return codeRepository.save(code);
    }

    @Transactional
    public Code update(Long codeId, CodeDto.Update update) {
        Code code = tryGetCode(codeId);
        BeanUtils.copyProperties(update, code);
        return codeRepository.save(code);
    }

    @Transactional
    public void delete(Long codeId) {
        Code code = tryGetCode(codeId);
        codeRepository.delete(code);
    }

    private Code tryGetCode(Long codeId) {
        Code code = codeRepository.findOne(codeId);
        if (code == null) {
            throw new RuntimeException("not.found.code");
        }
        return code;
    }
}
