package io.redutan.springboot.jpamall.account;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
		// TODO createAccount
		if (result.hasErrors()) {
			// TODO 에러응답 본문 추가
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		Account saved = service.createAccount(create);
		return new ResponseEntity<>(modelMapper.map(saved, AccountDto.Response.class),
				HttpStatus.CREATED);
	}
}
