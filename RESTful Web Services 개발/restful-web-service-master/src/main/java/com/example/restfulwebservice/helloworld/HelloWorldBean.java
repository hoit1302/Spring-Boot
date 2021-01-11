package com.example.restfulwebservice.helloworld;
// lombok ㅡ> 빈 클래스를 만들 때, Constructor/setter/getter 자동 생성

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// @RequiredArgsConstructor:  final이나 @NonNull인 필드 값만 파라미터로 받는 생성자 생성
@Data // @Getter, @Setter, @RequiredArgsConstructor, @ToString, @EqualsAndHashCode을 한번에 설정
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자 생성
@NoArgsConstructor // 파라미터가 없는 기본 생성자를 생성
// 직접 내가 생성자를 만들면 중복되어 오류(lombok plugin이 오류 알려줌)
public class HelloWorldBean {
    private String message;
}
