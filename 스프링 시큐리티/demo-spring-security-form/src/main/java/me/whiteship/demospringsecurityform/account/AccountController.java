package me.whiteship.demospringsecurityform.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

	@Autowired
	AccountService accountService;

	// 절대 이렇게 만들면 안되는 거 잘 알고 있음.
	// localhost:8080/account/ROLE/jueun/123
	// localhost:8080/account/ADMIN/adminn/ghgh1
	@GetMapping("/account/{role}/{username}/{password}")
	public Account createAccount(@ModelAttribute Account account) { // url에 있는 3개의 객체로 account 객체를 만들어 바인딩받음.
		return accountService.createNew(account); // 저장되는 값을 JSON으로 보여줌. RestController니.
	}


	// 회원가입의 경우, spring security 기능과 크게 관련이 없음.
	// password encoding만 잘하면 됨.
	// 브라우저에서 form 인증 말고, HTTP BASIC 인증 이라는 스펙이 있음. http 헤더에 특정한 값을 넣어 요청을 보내는. > HTTPS 를 써야함.
}
