package com.example.restfulwebservice.exception;

import com.example.restfulwebservice.user.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestController
@ControllerAdvice // 모든 @Controller 즉, 전역에서 발생할 수 있는 예외를 잡아 처리
// entity 뜻: 개방형 시스템 간 상호 접속의 계층 구조에서 서비스를 받거나 서비스를 제공하는 하나의 단위
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class) // Exception이 발생하면 실행된다.
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        // 예외 관련 정보 담기(ExceptionResponse은 내가 만든 클래스)
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));
        // ResponseEntity: HTTP 상태 코드와 전송하고 싶은 데이터와 함께 전송할 수 있다.
        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR); // 알려줄 에러 코드 지정
    }

    @ExceptionHandler(UserNotFoundException.class) // UserNotFoundException이 발생하면 실행된다.
    public final ResponseEntity<Object> handleUserNotFoundException(Exception ex, WebRequest request) {
        // ex.getMessage()을 통해 내가 지정한 메세지를 볼려올 수 있다. 메세지 지정은 예외 클래스에서 super(message)로 한다.
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(new Date(), ex.getMessage(), request.getDescription(false));

        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {

        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
                "Validation Failed", ex.getBindingResult().toString());
        // "timestamp": new Date()
        // "message": ex.getMessage() ㅡ> "Validation Failed" (중복되고 너무 길기 때문에 수정함)
        // "details": ex.getBindingResult().toString() (@Size(message="") 메세지로 지정한 문자도 출력됨.)
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
