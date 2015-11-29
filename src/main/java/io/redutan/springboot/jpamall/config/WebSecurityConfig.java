package io.redutan.springboot.jpamall.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author redutan
 * @since 2015. 9. 13.
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();

		// 실제 운영 시에는 절대로 켜면 안되는 옵션
		http.httpBasic();

		// white list 방식으로 보안제어
		http.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/accounts/**").hasRole("USER")
				.antMatchers(HttpMethod.PUT, "/accounts/**").hasRole("USER")
				.antMatchers(HttpMethod.DELETE, "/accounts/**").hasRole("USER")
//				.antMatchers(HttpMethod.GET, "/codes/**").hasRole("USER")
//				.antMatchers(HttpMethod.PUT, "/codes/**").hasRole("USER")
//				.antMatchers(HttpMethod.DELETE, "/codes/**").hasRole("USER")
				.anyRequest().permitAll();
	}

	// TODO ROLE hierachy
}
