package com.jueun.springmvcdemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig2 implements WebMvcConfigurer {
    // 스프링 MVC 설정을 내가 직접 모두 재정의. ㅡ> 거의 하지 않는다.
}
