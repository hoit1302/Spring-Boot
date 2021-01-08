package com.jueun.demo;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

// test/resources에 application.properties를 지우고 test.properties을 썼다.
// 빌드를 할 때 src 밑에 있는 것(src/main/java, src/main/resources)을 빌드를 해서 classpath에 놓고
// 그 다음에 test 코드를 빌드해서 classpath에 놓는다.
// 그 때 application.properties와 test 폴더 밑에 있는 test.properties는 파일 명이 다르기 때문에 둘 다 온전히 있다.
// 그런데 실행한 이 테스트에서 @TestPropertySource(locations = "classpath:/test.properties")로
// 추가로 test.properties을 사용하라고 한다.
// 2순위로 우선순위가 높기에 동일한 키값이 있을 때만, 오버라이딩한다.
// 그래서 main 밑 application.properties 에 있는 JUEUN이 아닌, test.properties에 있는 jueun22가 확인된다.
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:/test.properties") // 2순위로, 재정의하면 오버라이딩된다.
@SpringBootTest
class ApplicationTests {

    @Autowired
    Environment environment; // 모든 프로퍼티들은 Environment를 통해서 확인할 수 있다, 꼭 스프링에 있는 것을 써야 한다.

    @Test
    void contextLoads() {
        assertThat(environment.getProperty("jueun.name"))
                .isEqualTo("jueun22"); // assertThat은 static import 시키기!
    }

}
