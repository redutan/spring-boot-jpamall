package io.redutan.springboot.jpamall.code;

import io.redutan.springboot.Application;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author myeongju.jung
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})  // 위 설정(주석처리)이 이와같이 더 간결해진다.
@WebAppConfiguration
@Transactional
@Slf4j
public class CodeRepositoryTest {
    @Autowired
    CodeRepository repository;

    @Test
    public void testFindAllByParentIsNull() throws Exception {
        Code code1 = new Code();
        code1.setName("code1");

        repository.save(code1);

        // When
        List<Code> codes1 = repository.findAllByParentIsNull();

        assertThat(codes1.size(), is(1));
    }

    @Test
    public void testFindAllByParent() throws Exception {
        Code parentCode1 = new Code();
        parentCode1.setName("parentCode1");

        Code savedParentCode1 = repository.save(parentCode1);

        Code code1 = new Code();
        code1.setName("code1");
        code1.setParent(savedParentCode1);

        repository.save(code1);

        // When
        List<Code> codes1 = repository.findAllByParent(parentCode1);

        assertThat(codes1.size(), is(1));
        Code code2 = codes1.get(0);
        assertThat(code2, is(code1));
        assertThat(code1.getParent(), is(savedParentCode1));
    }

    @Test
    public void testFindAllByParentCodeId() throws Exception {
        Code parentCode1 = new Code();
        parentCode1.setName("parentCode1");

        Code savedParentCode1 = repository.save(parentCode1);

        Code code1 = new Code();
        code1.setName("code1");
        code1.setParent(savedParentCode1);

        repository.save(code1);

        // When
        List<Code> codes1 = repository.findAllByParentCodeId(savedParentCode1.getCodeId());

        assertThat(codes1.size(), is(1));
        Code code2 = codes1.get(0);
        assertThat(code2, is(code1));
        assertThat(code1.getParent(), is(savedParentCode1));
    }
}