package com.jueun.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Order(2)
public class Runner2 implements CommandLineRunner {
    // low하게 argument에 접근함
    @Override
    public void run(String... args) throws Exception {
        Arrays.stream(args).forEach(System.out::println);
        System.out.println("@Order(2)");
    }
}
