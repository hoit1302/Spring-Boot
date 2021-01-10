package com.jueun.springmvcdemo.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest2 {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void hello() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("hello"));
    }

    @Test // JSON 요청, XML로 응답받는 테스트
    public void createUser_XML() throws Exception {
        String userJson = "{\"username\":\"jueun\", \"password\":\"123\"}";
        mockMvc.perform(post("/users/create")
                .contentType(MediaType.APPLICATION_JSON) //JSON으로 요청
                .accept(MediaType.APPLICATION_XML) // 웅답을 XML로 받아보자
                .content(userJson))
                .andDo(print()) // 실패 시 자동으로 실행됨.
                .andExpect(status().isOk())
                .andExpect(xpath("/User/username").string("jueun"))
                .andExpect(xpath("/User/password").string("123"));
    }

}
