package com.jueun.springmvcdemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // 추천하는 방법. 기존의 resource handler 유지, 개발자가 원하는 것만 추가할 수 있음
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/m/**")
                .addResourceLocations("classpath:/m/") // 반드시 슬래시로 끝나야 함!
                .setCachePeriod(20); // 초 단위
    }
}
