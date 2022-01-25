package me.whiteship.demospringsecurityform.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


//authentication을 할 때, data access object를 통해 (in-memory가 아니라) 저장소를 통해 인증할 때 사용하는 interface
@Service
public class AccountService implements UserDetailsService {

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accountRepository.findByUsername(username);
		if (account == null) {
			throw new UsernameNotFoundException(username);
		}
		// Account 를 UserDetails 로 변환하는데 편리한 클래스인 User를 제공함.
		return User.builder()
				.username(account.getUsername())
				.password(account.getPassword())
				.roles(account.getRole())
				.build();
//		return new UserAccount(account);
	}

	public Account createNew(Account account) {
		account.encodePassword(passwordEncoder);
		return this.accountRepository.save(account);
	}
}

