package com.jueun.springtestdemo.sample;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

class SampleControllerTest2 {

    @Autowired // test용 RestTemplate
    TestRestTemplate testRestTemplate;

    @Test
    public void hello() throws Exception{
        // 이 body type에 있는 객체를 받음.
        String result = testRestTemplate.getForObject("/hello", String.class);
        assertThat(result).isEqualTo("hello jueun");
    }
}