package io.redutan.springboot.jpamall.account;

import io.redutan.springboot.common.ErrorResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author redutan
 */
@RestController
public class AccountController {

	@Autowired
	private AccountService service;

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

	@ExceptionHandler(UserDuplicatedException.class)
	public ResponseEntity handleUserDuplicatedException(UserDuplicatedException ude) {
		ErrorResponse errorResponse = new ErrorResponse("중복된 사용자명입니다. : [" + ude.getUsername() + "]",
				"duplicated.username.exception");
		return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
	}
}