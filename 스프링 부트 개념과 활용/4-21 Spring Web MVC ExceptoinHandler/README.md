**스프링 @MVC 예외 처리 방법** ㅡ> 이에 대해 코드 구현

- @ControllerAdvice
- @ExceptionHandler
- ![image](https://user-images.githubusercontent.com/68107000/104814511-b1e3f000-5852-11eb-960b-d74bf1bd547f.png)

**스프링 부트가 제공하는 기본 예외 처리기**

- BasicErrorController
  - HTML과 JSON 응답 지원
- 커스터마이징 방법
  - ErrorController 구현하는데, BasicErrorController를 상속받아 구현하는 것도 괜찮다. (직접 해보지는 않음)

**커스텀 에러 페이지**

- 상태 코드 값에 따라 에러 페이지 보여주기
- src/main/resources/static/error/|src/main/resources/template/error/
- 404.html
- 5xx.html
- ErrorViewResolver 구현할 수도 있음 (뭐 동적으로 서버 개발자에게 보내시겠습니까? 이런 버튼)