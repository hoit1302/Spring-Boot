package com.jueun.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    // Third-party Configuration
    // 이 애플리케이션 안에 있는 것이 아니라 JAR 파일 안에 있거나 스프링이 제공하는 프로퍼티클래스는 @Component를 못 붙이니까 @Bean으로.
    @ConfigurationProperties("server")
    @Bean
    public ServerProperties serverProperties(){
        return new ServerProperties();
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);
    }

}
