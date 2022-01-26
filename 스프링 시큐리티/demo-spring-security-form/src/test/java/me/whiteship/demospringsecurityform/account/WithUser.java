package me.whiteship.demospringsecurityform.account;

import org.springframework.security.test.context.support.WithMockUser;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME) // 이 annotation 정보를 runtime 까지 유지.
@WithMockUser(username = "keesun", roles = "USER")
public @interface WithUser {
}