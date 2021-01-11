package com.example.restfulwebservice.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// HTTP Status code
// 2XX -> OK
// 4XX -> Client (존재하지 않는 리소스 요청, 권한이 없거나, 제공하지 않는 메소드를 호출한다던가)
// 5XX -> Server
@ResponseStatus(HttpStatus.NOT_FOUND) //응답 상태 코드 값 지정하기
// CustomizedResponseEntityExceptionHandler.class에서 UserNotFoundException가 발생했을 때
// ExceptionResponse를 반환하고, 그때 HttpStatus 코드를 함께 반환하게 된다.
// 따라서 @ResponseStatus는 생략 가능하다.
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message); // 이 메세지 지정은 ex.getMessage()로 불러올 수 있음!
    }
}
