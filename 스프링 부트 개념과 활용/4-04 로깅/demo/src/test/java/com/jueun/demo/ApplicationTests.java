package com.jueun.demo;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
class ApplicationTests {

    @Autowired
    Environment environment; // 모든 프로퍼티들은 Environment를 통해서 확인할 수 있다, 꼭 스프링에 있는 것을 써야 한다.

    @Test
    void contextLoads() {
        assertThat(environment.getProperty("jueun.name"))
                .isEqualTo("hoit1302"); // assertThat은 static import 시키기!
    }

}
