package com.jueun.springtestdemo.sample;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(SampleController.class)
// 참고사항
// WebMvcTest auto-configures the Spring MVC infrastructure and limits scanned beans to
// @Controller, @ControllerAdvice, @JsonComponent, 웹 관련들만.
// Regular @Component and @ConfigurationProperties beans are not scanned
class SampleControllerTest6 {

    @Autowired // WebMvcTest는 항상 MockMvc로 test 해야 한다.
    MockMvc mockMvc;

    @MockBean // Service는 등록이 되지 않기 때문에 사용하는 의존성을 채워줘야 한다.
    SampleService mockSampleService;

    @Test
    public void hello() throws Exception{
        when(mockSampleService.getName()).thenReturn("jueun");

        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("hello jueun"));
    }
}