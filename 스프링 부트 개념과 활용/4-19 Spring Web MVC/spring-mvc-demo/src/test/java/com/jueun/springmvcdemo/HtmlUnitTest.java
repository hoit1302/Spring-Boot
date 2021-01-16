package com.jueun.springmvcdemo;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlHeading1;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

//HtmlUnit: html 보다 더 전문적으로 단위테스트하기 위한 tool 이다.
//WebClient에게 요청을 보내고 받아서 확인한다.

@RunWith(SpringRunner.class)
@WebMvcTest(SampleController.class)
public class HtmlUnitTest {

    @Autowired
    WebClient webClient;

    @Test
    public void hello() throws Exception {
        HtmlPage htmlPage = webClient.getPage("/hello");
        HtmlHeading1 h1 = htmlPage.getFirstByXPath("//h1");
        assertThat(h1.getTextContent()).isEqualToIgnoringCase("jueun");
    }


}

