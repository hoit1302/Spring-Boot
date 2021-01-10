package com.jueun.springmvcdemo.user;

import org.springframework.web.bind.annotation.*;

@RestController // @ResponseBody가 포함되어 있다.
public class UserController {

    @GetMapping("/hello")
    public String hello(){ // @ResponseBody가 String 앞에 생략되어 있다.
        return "hello";
    }

    @PostMapping("/users/create") // @ResponseBody가 User 앞에 생략되어 있다.
    public User create(@RequestBody User user) {
        return user;
    }
}
