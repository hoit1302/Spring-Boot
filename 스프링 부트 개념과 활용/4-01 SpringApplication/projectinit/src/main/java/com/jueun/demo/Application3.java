package com.jueun.demo;

import org.springframework.boot.SpringApplication;

public class Application3 {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.addListeners(new SampleListener());
        app.run(args);
    }
}
