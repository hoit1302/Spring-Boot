package com.jueun.springtestdemo.sample;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient // WebTestClient 빈을 만들어 줌. 이를 안써도 webflux 의존성을 추가하면 정상작동한다.
class SampleControllerTest4 {

    // 기존에 사용하던 RestClient는 synchronous였는데 webClient는 asynchronous
    // synchronous - 요청하나 보내고 끝날 때까지 기다린 다음에 그 다음 요청을 보낼 수 있음
    // asynchronous - 요청을 보내고 기다리는 것이 아니라 응답이 오면 그 때 call back이 온다.
    // 하지만 꼭 이것 때문이 아니더라도 api가 편해서 쓴다고 한다.
    // + 추가로, mockMvc이 두번째로 불편하고, RestTemplate이 제일 불편하다고 한다.
    // spring-boot-starter-webflux 의존성을 추가해야한다.
    @Autowired
    WebTestClient webTestClient;

    @MockBean
    SampleService mockSampleService;

    @Test
    public void hello() throws Exception{
        when(mockSampleService.getName()).thenReturn("hoit");

        webTestClient.get().uri("/hello").exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("hello hoit");
    }
}