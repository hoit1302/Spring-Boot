package me.whiteship.demospringsecurityform.config;

import me.whiteship.demospringsecurityform.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//	url 별로 요청에 대해 다르게 허용하는데, 특정한 역할을 요구할 수도 있음.
		http.authorizeRequests()
				.mvcMatchers("/", "/info", "/account/**").permitAll()
				.mvcMatchers("/admin").hasRole("ADMIN")
				.anyRequest().authenticated();
		//	and로 모두 이어붙일 수도 있고, 따로 작성할 수도 있음.
//		... .anyRequest().authenticated().and().formLogin(). ...
		http.formLogin();
		http.httpBasic();
	}
//  이렇게 명시적으로 설정하지 않아도 UserDetailsService 타입의 빈이 등록만 되어 있으면 가져다가 쓰게 됨.
/*
	@Autowired
	AccountService accountService;

	// AuthenticationManagerBuilder야, UserDetailsService 구현체인 AccountService를 써서 user 정보를 db에서 가져와 쓰렴.
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		super.userDetailsService(accountService);
	}
*/

}
