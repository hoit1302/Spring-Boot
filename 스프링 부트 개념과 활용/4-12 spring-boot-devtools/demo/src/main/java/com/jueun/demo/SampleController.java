package com.jueun.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    @Autowired
    private SampleService sampleService;

    @GetMapping("/hello")
    public String hello(){
        System.out.println(sampleService.getName() + " 들어옴");
        return "hello " + sampleService.getName();
    }
}
