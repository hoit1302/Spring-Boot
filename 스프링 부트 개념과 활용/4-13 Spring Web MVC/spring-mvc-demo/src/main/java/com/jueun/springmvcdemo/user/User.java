package com.jueun.springmvcdemo.user;

public class User {

    private Long id;
    private String username;
    private String password;

    // java bean 규약에 따라 getter,setter가 있어야 한다.
    // getter,setter를 이용해서 바인딩되기 때문이다.

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
