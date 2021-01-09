package com.jueun.springtestdemo.sample;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SampleControllerTest3 {

    @Autowired // test용 RestTemplate
    TestRestTemplate testRestTemplate;

    @MockBean     // SampleController가 쓰는 SampleService를 모킹해서 빈을 교체했다.
    SampleService mockSampleService;
    // ApplicationContext 안에 있는 SampleService 빈을 Mock으로 만든 mockSampleService로 교체한다.
    // 그래서 이 테스트에서 Service는 원본 SampleService가 아닌 mockSampleService를 쓰게 된다.
    // 이제 mocking을 할 수 있다.

    @Test
    public void hello() throws Exception{
        when(mockSampleService.getName()).thenReturn("hoit");

        String result = testRestTemplate.getForObject("/hello", String.class);
        assertThat(result).isEqualTo("hello hoit");
    }
}