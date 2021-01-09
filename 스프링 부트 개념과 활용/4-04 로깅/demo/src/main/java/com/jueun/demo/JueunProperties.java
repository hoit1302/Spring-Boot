package com.jueun.demo;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

// 원래 @ConfigurationProperties을 사용하려면
// @EnableConfigurationProperties(JueunProperties.class) 설정해주어야 하지만
// 내부적으로 @EnableConfigurationProperties 등록이 되어 있고
// 우리가 해야 할 일은 @Component를 붙여주는 일 뿐이다.
@Component
@ConfigurationProperties("jueun") // jueun이라는 키 값으로 프로퍼티 사용. getter/setter 필요
public class JueunProperties {

    private String name;
    private int age;
    private String fullName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
