package io.redutan.springboot.jpamall.account;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

/**
 * @author redutan
 */
@Service
@Transactional
public class AccountService {

	@Autowired
	private AccountRepository repository;

	@Autowired
	private ModelMapper modelMapper;

	public Account createAccount(AccountDto.Create create) {

		// #1 dto -> entity 수동 변환
//		Account account = new Account();
//		account.setUsername(create.username);
//		account.setPassword(create.password);

		// #2 dto -> entity beansUtils(스프링제공)을 통한 변환
//		Account account = new Account();
//		BeanUtils.copyProperties(create, account);

		// #3 dto -> entity modelMapper를 통한 변환
		Account account = modelMapper.map(create, Account.class);
		/*
		 #1 수동 방식은 제외
		 #2 스프링 기본 제공 static 이기 때문에 라이브러리 의존성 추가과 DI 없이 사용가능
			하지만 우선객체를 생성한 후 속성을 카피하기 때문에 약간 아쉬움. - 옵션이 기본적임
		 #3 modelMapper 오픈소스 라이브러리를 통한 객체변환
			가장 심플하나 우선 추천안 - 다양한 옵션 제공. BeanUtils과 성능 비교 후 사용요망
		 */

		// TODO 유효한 username 판단
		String username = create.getUsername();
		if (repository.findByUsername(username) != null) {
			throw new UserDuplicatedException(username);
		}

		// TODO password 단방향암호화

		Date now = new Date();
		account.setJoined(now);
		account.setUpdated(now);

		return repository.save(account);
	}
}
