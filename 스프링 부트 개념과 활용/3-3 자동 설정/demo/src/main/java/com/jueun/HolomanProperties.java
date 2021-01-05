package com.jueun;


import org.springframework.boot.context.properties.ConfigurationProperties;

// 메인프로젝트에서 holoman로 시작하여 프로퍼트로 접근한다.
@ConfigurationProperties("holoman")
public class HolomanProperties {
    private  String name;
    private int howLong;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHowLong() {
        return howLong;
    }

    public void setHowLong(int howLong) {
        this.howLong = howLong;
    }
}
