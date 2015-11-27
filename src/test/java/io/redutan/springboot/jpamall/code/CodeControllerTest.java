package io.redutan.springboot.jpamall.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.redutan.springboot.Application;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author myeongju.jung
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(loader = SpringApplicationContextLoader.class, classes = {Application.class})
@SpringApplicationConfiguration(classes = {Application.class})  // 위 설정(주석처리)이 이와같이 더 간결해진다.
@WebAppConfiguration
@Transactional
@Slf4j
public class CodeControllerTest {
    @Autowired
    WebApplicationContext wac;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    CodeRepository repository;

    MockMvc mockMvc;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilter(springSecurityFilterChain)
                .build();
    }

    @Test
    public void testCodes() throws Exception {

    }

    @Test
    public void testInsert() throws Exception {
        // Given
        CodeDto.Create create = new CodeDto.Create();
        create.setName("코드명");

        log.info("create = {}", create);

        String content = objectMapper.writeValueAsString(create);
        log.info("content1 = {}", content);

        // When
        ResultActions result = mockMvc.perform(post("/codes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        // Then
        result.andDo(print());
        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.name", is("코드명")));
    }

    @Test
    public void testUpdate() throws Exception {

    }

    @Test
    public void testRemove() throws Exception {

    }
}