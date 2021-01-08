package com.jueun.demo;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1) // 순서 지정 가능. 숫자 낮은 게 먼저 실행
public class Runner implements ApplicationRunner {
    // 고급진 API를 통해 argument를 쓸 수 있음. (추천)
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("@Order(1)");
        System.out.println("foo: " + args.containsOption("foo")); // VM options: -Dfoo
        System.out.println("bar: " + args.containsOption("bar")); // Program arguments: --bar
    }
}
