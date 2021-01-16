package com.jueun.jpa.account;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Account {

    @Id
    @GeneratedValue
    private Long id;
    private String username; // 이름을 변경하고 싶다면 1. 새컬럼 추가 2. 기존내용 붙여넣고 3.기존컬럼 삭제
    private String password;
    private boolean active;
}
