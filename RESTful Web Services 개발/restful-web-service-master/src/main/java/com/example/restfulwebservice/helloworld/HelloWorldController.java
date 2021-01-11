package com.example.restfulwebservice.helloworld;

import com.example.restfulwebservice.user.User;
import com.example.restfulwebservice.user.UserDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloWorldController {

    @Autowired // 주입 종류: 생성자 주입, setter 메서드 주입, @Autowired
    private MessageSource messageSource;

    // GET
    // /hello-world (endpoint)
    // @RequestMapping(method=RequestMethod.GET, path="/hello-world")
    @GetMapping(path = "/hello-world")
    public String helloWorld() {
        return "Hello World";
    }

    // alt + enter
    @GetMapping(path = "/hello-world-bean")
    public HelloWorldBean helloWorldBean() { // java bean 형태로 반환 (lombok 배움)
        return new HelloWorldBean("Hello World");
    }

    @GetMapping(path = "/hello-world-bean/path-variable/{name}")
    public HelloWorldBean helloWorldBean(@PathVariable String name) {
        return new HelloWorldBean(String.format("Hello World, %s", name));
    }

    // 다국어처리 관련 내용
    @GetMapping(path = "/hello-world-internationalized")
    public String helloWorldInternationalized(
            @RequestHeader(name="Accept-Language", required=false)  Locale locale)
            // "Accept-Language" 라는 헤더 값이 있으면 locale에 저장
            // required=false : 필수아님
            // 지정 안하면 기본 local 값
    {
        return messageSource.getMessage("greeting.message", null, locale);
        // 첫번째 인자: 설정 파일에서의 key 값
        // 두번째 인자: 만약 parameter를 가진 가변변수라면 parameter 지정
        // RequestHeader를 통해 전달받은 locale
    }
}
