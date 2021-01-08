package com.jueun.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("prod") // 설정파일 자체가 "prod"라는 프로파일일 때 사용된다. 그러니까 당연히 hello 빈도 못쓰겠죠?
@Configuration
public class BaseConfiguration {

    @Bean
    public String hello(){
        return "hello";
    }
}
