package com.jueun.springmvcdemo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SampleController {

    @GetMapping("/hello")
    public String hello(Model model){
        model.addAttribute("name", "jueun");// model을 Map처럼 사용
        return "helloEveryone"; // 뷰 이름을 전달. @RestController가 아니니까 본문의 내용이 아님.
    }
}
