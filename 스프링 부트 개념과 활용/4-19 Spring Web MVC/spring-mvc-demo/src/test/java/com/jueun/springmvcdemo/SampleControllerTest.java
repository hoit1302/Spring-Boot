package com.jueun.springmvcdemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(SampleController.class)
public class SampleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void hello() throws Exception {
        // 요청 "/hello"
        // 응답
        // - 모델 name : jueun
        // - 뷰 이름 : helloEveryone

        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andDo(print()) // 렌더링 본문 확인 가능
                // 타임리프 엔진은 서블릿 컨테이너에 독립적인 엔진이기에 view에 렌더링 결과값도 확인할 수 있다.
                .andExpect(view().name("helloEveryone"))
                .andExpect(model().attribute("name", "jueun"))
                .andExpect(content().string(containsString("jueun")));
    }
}