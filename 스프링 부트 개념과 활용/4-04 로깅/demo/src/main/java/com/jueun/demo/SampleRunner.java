package com.jueun.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class SampleRunner implements ApplicationRunner {

    @Autowired
    JueunProperties jueunProperties;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("=====================");
        System.out.println(jueunProperties.getName());
        System.out.println(jueunProperties.getAge());
        System.out.println(jueunProperties.getFullName());
        System.out.println(jueunProperties.getSessionTimeout());
        System.out.println("=====================");
    }
}
