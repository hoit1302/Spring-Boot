package me.whiteship.demospringsecurityform.account;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class) // JNUIT4 기반
@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	AccountService accountService;

	/**
	 * url에 따른 접근 권한 테스트
 	 */
	@Test
	@WithAnonymousUser
	public void index_anonymous() throws Exception {
		mockMvc.perform(get("/"))
				.andDo(print())
				.andExpect(status().isOk());
	}

	// 아래와 같이 가짜 유저가 있다고 가정을 함. <mocking>
	// 현재 이미 가짜 유저가 로그인한 상태일 때, /admin 페이지를 요청하면 어떻게 될 것인가? O
	// 이 유저가 이미 db에 있고 로그인해서 /admin 페이지를 요청한다 X
//	mockMvc.perform(get("/admin").with(user("keesun").roles("USER")))
	@Test
//	@WithAnonymousUser // AnonymousUser로 로그인 상태로 실행
	@WithUser	// 근데 이 코드를 커스텀한 어노테이션으로 재사용할 수 있음.
	public void index_user() throws Exception {
		mockMvc.perform(get("/"))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	@WithUser
	public void admin_user() throws Exception {
		mockMvc.perform(get("/admin"))
				.andDo(print())
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "keesun", roles = "ADMIN")
	public void admin_admin() throws Exception {
		mockMvc.perform(get("/admin"))
				.andDo(print())
				.andExpect(status().isOk());
	}

	/**
	 * 폼 인증 관련 테스트
	 */
	// 참고: formLogin() 에서 체인으로 연결됨. SecurityConfig 파일에서 http.formLogin() 관련 설정을 테스틀할 수 있음.
	@Test
	@Transactional
	public void login_success() throws Exception {
		// 단축키 - extract variable: option command V
		String username = "keesun";
		String password = "123";
		Account user = this.createUser(username, password); // 가짜 유저를 만든 다음
		mockMvc.perform(formLogin().user(user.getUsername()).password(password))
				.andExpect(authenticated()); // 인증되는지 확인
	}

	@Test
	@Transactional // 붙이지 않으면 3개의 메서드에서 같은 user를 추가하고 있기에, username이 중복되어 셋 중 두 개는 랜덤으로 fail
	// 각각의 테스트가 끝난 다음 rollback이 되도록, db에 변경사항을 반영하지 않도록, transactional을 붙여줌.
	// 서로서로 독립적인 테스트가 됨. 이게 좋은 거.
	public void login_success2() throws Exception {
		String username = "keesun";
		String password = "123";
		Account user = this.createUser(username, password);
		mockMvc.perform(formLogin().user(user.getUsername()).password(password))
				.andExpect(authenticated());
	}

	@Test
	@Transactional()
	public void login_fail() throws Exception {
		String username = "keesun";
		String password = "123";
		Account user = this.createUser(username, password);
		mockMvc.perform(formLogin().user(user.getUsername()).password("12345"))
				.andExpect(unauthenticated());
	}

	// 단축키 - extract method: option command M
	private Account createUser(String username, String password) {
		Account account = new Account();
		account.setUsername(username);
		account.setPassword(password);
		account.setRole("USER");
		return accountService.createNew(account);
	}

}