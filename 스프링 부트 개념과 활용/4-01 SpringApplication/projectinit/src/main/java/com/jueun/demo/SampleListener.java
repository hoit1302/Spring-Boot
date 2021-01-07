package com.jueun.demo;

import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

// @Component 빈으로 등록하면 이벤트가 발생되었을 때 자동으로 실행이 된다.
// 하지만 applicationContext가 발생하기 이전에 실행되는 이벤트라서 리스러가 동작을 안한다.
// 이런 경우에는 직접 등록을 해주어야 한다. SpringApplication에. ㅡ> Application3 클래스에서 작동.
public class SampleListener implements ApplicationListener<ApplicationStartingEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartingEvent applicationStartingEvent) {
        System.out.println("======================");
        System.out.println("Application is starting");
        System.out.println("======================");
    }
}
