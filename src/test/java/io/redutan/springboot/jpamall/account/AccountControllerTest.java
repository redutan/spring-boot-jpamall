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

import static io.redutan.springboot.jpamall.account.AccountDto.*;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

	@Autowired
	AccountService service;

	MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac)
				.build();
	}

	@Test
	public void testCreateAccount() throws Exception {
		// Given
		Create createDto = new Create();
		createDto.setUsername("redutan");
		createDto.setPassword("password");

		// When
		ResultActions result = mockMvc.perform(post("/accounts")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(createDto)));

		// Then
		result.andDo(print());
		result.andExpect(status().isCreated());
		// "id":1,"username":"redutan","fullName":null,"joined":1441721929478,"updated":1441721929478}
		result.andExpect(jsonPath("$.username", is("redutan")));
	}

	@Test
	public void testCreateAccount_BadRequest() throws Exception {
		// Given
		Create createDto = new Create();
		createDto.setUsername("  ");        // blank
		createDto.setPassword("1234");      // invalid size (min = 5)

		// When
		ResultActions result = mockMvc.perform(post("/accounts")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(createDto)));

		// Then
		result.andDo(print());
		result.andExpect(status().isBadRequest());
		result.andExpect(jsonPath("$.code", is("bad.request")));
	}

	@Test
	public void testCreateAccount_DuplicatedUsername() throws Exception {
		// Given
		Create createDto = accountCreateDto();

		ResultActions result = mockMvc.perform(post("/accounts")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(createDto)));

		result.andDo(print());
		result.andExpect(status().isCreated());

		// When
		result = mockMvc.perform(post("/accounts")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(createDto)));

		// Then
		result.andDo(print());
		result.andExpect(status().isBadRequest());
		result.andExpect(jsonPath("$.code", is("duplicated.username.exception")));
	}

	@Test
	public void testGetAccounts() throws Exception {
		final String username = "redutan";
		// Given
		Create createDto = new Create();
		createDto.setUsername(username);
		createDto.setPassword("password");
		service.createAccount(createDto);

		// When
		ResultActions result = mockMvc.perform(get("/accounts"));

		// Then
		/*
			{"content":[{"id":1,"username":"redutan","fullName":null,"joined":1441725516380,"updated":1441725516380}],"totalPages":1,"totalElements":1,"last":true,"size":20,"number":0,"sort":null,"first":true,"numberOfElements":1}
		 */
		result.andDo(print());
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.content[0].username", is(username)));
		result.andExpect(jsonPath("$.totalElements", is(1)));
	}

	private Create accountCreateDto() {
		Create createDto = new Create();
		createDto.setUsername("redutan");
		createDto.setPassword("password");
		return createDto;
	}

	@Test
	public void testGetAccount() throws Exception {
		Create createDto = accountCreateDto();
		Account account = service.createAccount(createDto);

		ResultActions result = mockMvc.perform(get("/accounts/" + account.getId()));
		result.andDo(print());
		result.andExpect(status().isOk());
	}

	@Test
	public void testUpdateAccount() throws Exception {
		Create createDto = accountCreateDto();
		Account account = service.createAccount(createDto);

		Update updateDto = new Update();
		updateDto.setFullName("myeongju jung");
		updateDto.setPassword("pass");

		ResultActions result = mockMvc.perform(put("/accounts/" + account.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updateDto)));

		result.andDo(print());
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.fullName", is("myeongju jung")));
		result.andExpect(jsonPath("$.password", is("pass")));
	}
}