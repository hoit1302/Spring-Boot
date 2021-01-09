package com.jueun.springtestdemo.sample;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.system.OutputCaptureRule;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@WebMvcTest(SampleController.class)
public class SampleControllerTestUtil {

    @Rule
    public OutputCaptureRule output = new OutputCaptureRule();

    @MockBean     // SampleController가 쓰는 SampleService를 모킹해서 빈을 교체했다.
    SampleService mockSampleService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void hello() throws Exception{
        when(mockSampleService.getName()).thenReturn("hoit");

        mockMvc.perform(get("/hello2"))
                .andExpect(content().string("hello hoit"));

        assertThat(output.toString())
                .contains("info level")
                .contains("sout");
    }
}