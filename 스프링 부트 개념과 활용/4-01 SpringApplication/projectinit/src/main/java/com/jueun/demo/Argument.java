package com.jueun.demo;

import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

@Component
public class Argument {
    // 생성자의 파라미터가 빈일때는 스프링이 자동으로 주입해준다.

    public Argument(ApplicationArguments arguments) {
        System.out.println("foo: " + arguments.containsOption("foo")); // VM options: -Dfoo
        System.out.println("bar: " + arguments.containsOption("bar")); // Program arguments: --bar
        // 출력값
        // foo: false
        // bar: true
    }
}
