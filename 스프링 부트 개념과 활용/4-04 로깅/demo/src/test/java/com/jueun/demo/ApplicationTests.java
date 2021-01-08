package com.jueun.demo;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
// @TestPropertySource(properties = "jueun.name=jueun2") // 2순위로, 재정의하면 오버라이딩된다.
@SpringBootTest
@SpringBootTest(properties = "jueun.name=jueun3") // 3순위로, 재정의하면 오버라이딩된다.
class ApplicationTests {

    @Autowired
    Environment environment; // 모든 프로퍼티들은 Environment를 통해서 확인할 수 있다, 꼭 스프링에 있는 것을 써야 한다.

    @Test
    void contextLoads() {
        assertThat(environment.getProperty("jueun.name"))
                .isEqualTo("jueun3"); // assertThat은 static import 시키기!
    }

}
