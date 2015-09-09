package io.redutan.springboot.jpamall.account;

import io.redutan.springboot.jpamall.common.ErrorResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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

	@RequestMapping(value = "/accounts", method = RequestMethod.POST)
	public ResponseEntity createAccount(@RequestBody @Valid AccountDto.Create create,
	                                    BindingResult result) {
		if (result.hasErrors()) {
			ErrorResponse errorResponse = new ErrorResponse("잘못된 요청입니다.", "bad.request");
			// TODO BindingResult 안에 있는 에러정보를 이용해서 오류가 발생한 속성과 오류내용까지 표기하기
			return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
		}
		Account saved = service.createAccount(create);
		// 오류 확인
		/*
			 #1 반환값 null로 확인
			 #2 parameter 에 error이 존재하는가? : #1 보다 직관적
			 #3 exception을 던진다.

		 */
		return new ResponseEntity<>(modelMapper.map(saved, AccountDto.Response.class),
				HttpStatus.CREATED);
	}

	// /accounts?page=0&size=20&sort=username&sort=joined,desc
	@RequestMapping(value = "/accounts", method = RequestMethod.GET)
	public ResponseEntity getAccounts(Pageable pageable) {
		Page<Account> page = repository.findAll(pageable);
		List<AccountDto.Response> content = page.getContent().stream()
				.map(account -> modelMapper.map(account, AccountDto.Response.class))
				.collect(Collectors.toList());
		PageImpl<AccountDto.Response> result = new PageImpl<>(content, pageable, page.getTotalElements());
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@ExceptionHandler(UserDuplicatedException.class)
	public ResponseEntity handleUserDuplicatedException(UserDuplicatedException ude) {
		ErrorResponse errorResponse = new ErrorResponse("중복된 사용자명입니다. : [" + ude.getUsername() + "]",
				"duplicated.username.exception");
		return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
	}

	// TODO 비동기방식(람다식) exception 핸들링
	// TODO stream() vs parallelStream()
	// TODO HATEOAS
	// TODO 뷰 : Thymeleaf, react
}