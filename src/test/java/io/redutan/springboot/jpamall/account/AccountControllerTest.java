package io.redutan.springboot.jpamall.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.redutan.springboot.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
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
 * @author redutan
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(loader = SpringApplicationContextLoader.class, classes = {Application.class})
@SpringApplicationConfiguration(classes = {Application.class})  // 위 설정(주석처리)이 이와같이 더 간결해진다.
@WebAppConfiguration
@Transactional
public class AccountControllerTest {

	@Autowired
	WebApplicationContext wac;

	@Autowired
	ObjectMapper objectMapper;

	MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac)
				.build();
	}

	@Test
	public void testCreateAccount() throws Exception {
		AccountDto.Create createDto = new AccountDto.Create();
		createDto.setUsername("redutan");
		createDto.setPassword("password");

		ResultActions result = mockMvc.perform(post("/accounts")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(createDto)));

		result.andDo(print());
		result.andExpect(status().isCreated());
		// "id":1,"username":"redutan","fullName":null,"joined":1441721929478,"updated":1441721929478}
		result.andExpect(jsonPath("$.username", is("redutan")));
	}

	@Test
	public void testCreateAccount_BadRequest() throws Exception {
		AccountDto.Create createDto = new AccountDto.Create();
		createDto.setUsername("  ");        // blank
		createDto.setPassword("1234");      // invalid size (min = 5)

		ResultActions result = mockMvc.perform(post("/accounts")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(createDto)));

		result.andDo(print());
		result.andExpect(status().isBadRequest());
		result.andExpect(jsonPath("$.code", is("bad.request")));
	}

	@Test
	public void testCreateAccount_DuplicatedUsername() throws Exception {
		AccountDto.Create createDto = new AccountDto.Create();
		createDto.setUsername("redutan");
		createDto.setPassword("password");

		ResultActions result = mockMvc.perform(post("/accounts")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(createDto)));

		result.andDo(print());
		result.andExpect(status().isCreated());

		result = mockMvc.perform(post("/accounts")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(createDto)));

		result.andDo(print());
		result.andExpect(status().isBadRequest());
		result.andExpect(jsonPath("$.code", is("duplicated.username.exception")));
	}
}