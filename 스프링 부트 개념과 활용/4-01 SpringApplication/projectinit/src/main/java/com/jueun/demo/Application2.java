package com.jueun.demo;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.env.Environment;

import java.io.PrintStream;

@SpringBootApplication
public class Application2 {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
        .sources(Application2.class)
        .run(args);
    }

}
