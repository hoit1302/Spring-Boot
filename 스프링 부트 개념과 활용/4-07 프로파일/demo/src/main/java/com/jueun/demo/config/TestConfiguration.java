package com.jueun.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("test") // 설정파일 자체가 "test"라는 프로파일일 때 사용된다.
@Configuration
public class TestConfiguration {

    @Bean
    public String hello(){
        return "hello test";
    }
}
