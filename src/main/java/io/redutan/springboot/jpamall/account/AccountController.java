package io.redutan.springboot.jpamall.account;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author redutan
 */
@Controller
public class AccountController {

	@RequestMapping("/hello")
	@ResponseBody
	public String hello() {
		return "Hello Spring Boot!!!!";
	}
}
