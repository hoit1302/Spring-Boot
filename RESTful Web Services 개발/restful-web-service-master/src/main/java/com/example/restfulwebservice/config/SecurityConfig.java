package com.example.restfulwebservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // h2-console 요청은 permission 허용
        http.authorizeRequests().antMatchers("/h2-console/**").permitAll();
        http.csrf().disable(); // Cross-site request forgery 사용 안함
        http.headers().frameOptions().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
        throws Exception {
        auth.inMemoryAuthentication()
                .withUser("kenneth")
                .password("{noop}test1234") // {noop}: encoding 없이 사용할 수 있도록.
                .roles("USER"); // USER 권한 부여

        // username:kenneth, password:test1234 로 로그인하면 200OK
    }
}
