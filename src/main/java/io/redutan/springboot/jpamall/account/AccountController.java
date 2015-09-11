package io.redutan.springboot.jpamall.account;

import io.redutan.springboot.jpamall.common.ErrorResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static io.redutan.springboot.jpamall.account.AccountDto.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * @author redutan
 */
@RestController
public class AccountController {

	@Autowired
	private AccountService service;

	@Autowired
	private AccountRepository repository;

	@Autowired
	private ModelMapper modelMapper;

	@RequestMapping(value = "/accounts", method = POST)
	public ResponseEntity createAccount(@RequestBody @Valid Create create,
	                                    BindingResult result) {
		if (result.hasErrors()) {
			ErrorResponse errorResponse = new ErrorResponse("잘못된 요청입니다.", "bad.request");
			// TODO BindingResult 안에 있는 에러정보를 이용해서 오류가 발생한 속성과 오류내용까지 표기하기
			return new ResponseEntity(errorResponse, BAD_REQUEST);
		}
		Account saved = service.createAccount(create);
		// 오류 확인
		/*
			 #1 반환값 null로 확인
			 #2 parameter 에 error이 존재하는가? : #1 보다 직관적
			 #3 exception을 던진다.

		 */
		return new ResponseEntity<>(modelMapper.map(saved, Response.class),
				CREATED);
	}

	// /accounts?page=0&size=20&sort=username&sort=joined,desc
	@RequestMapping(value = "/accounts", method = GET)
	@ResponseStatus(OK)
	public PageImpl<AccountDto.Response> getAccounts(Pageable pageable) {
		Page<Account> page = repository.findAll(pageable);
		List<AccountDto.Response> content = page.getContent().stream()
				.map(account -> modelMapper.map(account, Response.class))
				.collect(Collectors.toList());
		PageImpl<AccountDto.Response> result = new PageImpl<>(content, pageable, page.getTotalElements());
		return result;
	}

	@RequestMapping(value = "/accounts/{id}", method = GET)
	@ResponseStatus(OK)
	public Response getAccount(@PathVariable Long id) {
		Account account = service.getAccount(id);
		Response result = modelMapper.map(account, Response.class);
		return result;
	}

	// 전체 업데이트 (PUT)
	// 부분 업데이트 (PATCH)
	@RequestMapping(value = "/accounts/{id}", method = PUT)
	public ResponseEntity updateAccount(@PathVariable Long id,
	                                    @RequestBody @Valid Update updateDto,
	                                    BindingResult result) {
		if (result.hasErrors()) {
			return new  ResponseEntity<>(BAD_REQUEST);
		}
		Account account = service.updateAccount(id, updateDto);
		return new ResponseEntity(account, OK);
	}


	@ExceptionHandler(AccountDuplicatedException.class)
	@ResponseStatus(BAD_REQUEST)
	public ErrorResponse handleUserDuplicatedException(AccountDuplicatedException e) {
		ErrorResponse errorResponse = new ErrorResponse("중복된 사용자명입니다. : [" + e.getUsername() + "]",
				"duplicated.username.exception");
		return errorResponse;
	}

	@ExceptionHandler(AccountNotFoundException.class)
	@ResponseStatus(BAD_REQUEST)
	public ErrorResponse handleAccountNotFoundException(AccountNotFoundException e) {
		ErrorResponse errorResponse = new ErrorResponse("[" + e.getId() + "] 에 해당하는 계정이 없습니다.",
				"account.not.found.exception");
		return errorResponse;
	}

	// TODO 비동기방식(람다식) exception 핸들링
	// TODO stream() vs parallelStream()
	// TODO HATEOAS
	// TODO 뷰 : Thymeleaf, react
}