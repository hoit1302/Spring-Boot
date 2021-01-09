package com.jueun.springtestdemo.sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    @Autowired
    private  SampleService sampleService;

    @GetMapping("/hello")
    public String hello() {
        return "hello " + sampleService.getName();
    }

    Logger logger = LoggerFactory.getLogger(SampleController.class);

    @GetMapping("/hello2")
    public String hello2() {
        logger.info("info level");
        System.out.println("sout");
        return "hello " + sampleService.getName();
    }
}
