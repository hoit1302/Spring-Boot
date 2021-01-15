# 1. 개념

### ORM(Object-Relational Mapping)과 JPA (Java Persistence API)

객체와 릴레이션을 맵핑할 때 발생하는 개념적 불일치를 해결하는 프레임워크
http://hibernate.org/orm/what-is-an-orm/
JPA: ORM을 위한 자바 (EE) 표준

### 스프링 데이터 JPA

- Repository 빈 자동 생성
- 쿼리 메소드 자동 구현
- @EnableJpaRepositories (스프링 부트가 자동으로 설정 해줌.)
- SDJ -> JPA -> Hibernate -> Datasource

![img](https://lh5.googleusercontent.com/ig8QawytDtqZk5hwqOwyhEUaPtHgks3BZblZKWSo7VQAe_g81TBIfgmL01CgHq3su4ZFnBNjcb-jhqegS06ptp_kvpLbTRO3TMOOPr-JNixC3dyMY4iAO34q4pKY9391V1xr3Sk4)

starter-data-jpa 는
starter-jdbc를 통해 DataSource JdbcTemplate 젤 밑에 있는? 걸 쓸 수 있음.
hibernate도 가져오고
orm도 가져오고,,,
그 이외에도 aop, aspect 등 감이오는 스프링프레임워크의 친구들이 있는 걸 보니 기분이 좋다.

# 2.

## Application

Postgresql 과 연결 (운영용)

## Test

- in-memory db 사용
  - 지원하는 인-메모리 데이터베이스
    - **H2 (추천, 사용)**	
    - HSQL
    - Derby

### @DataJpaTest

slicing test의 경우 자동으로 embeded db인 h2를 쓰도록 설정되어 있다.

in-memory database 가 반드시 필요하다.

장점: 훨씬 빠르고 test 용 db를 따로 만들지 않아도 된다.

### @SpringBootTest

Integration test ㅡ> Application이 사용하는 db를 사용한다.

------

```
데이터베이스 초기화

JPA를 사용한 데이터베이스 초기화
spring.jpa.hibernate.ddl-auto
create: 있다면 없애고 생성
create-drop: 애플리케이션 종료되면 drop
update: 변경된 부분에 대해서만 스키마 업데이트 ㅡ> 기존data 유지
하지만 username ㅡ> nickname 으로 변경 시 username 그대로, nickname 새로 생성되기 때문에 개발 시에는 유용하게 쓸 수 있으나 운영 X
spring.jpa.generate-dll=true로 설정 해줘야 동작함.
spring.jap.show-sql=ture 스키마를 

* 운영 용 안정적인 옵션은?
spring.jpa.hibernate.ddl-auto: validate // entity 매핑 relation db에 매핑이 되는 상황인지 검증하는 옵션
spring.jpa.generate-dll=false // dll 에 변경을 가할 것은 아니기에 꺼주고

SQL 스크립트를 사용한 데이터베이스 초기화
resources/schema.sql (순서1 dll) 
(spring.jpa.generate-dll=false 로 두어도 이미 스키마가 정의되어 있기 때문에 오류X)
data.sql (순서2 따라서 초기화데이터를 씀)

플랫폼에 따른 SQL 스크립트를 사용한 데이터베이스 초기화 설정을 할 수도 있다.
application.properties: spring.datasource.platform=${platform} (ex, postgresql)
schema-${platform}.sql
data-${platform}.sql

```

-----

강사님은: test 코드로 hibernate: 스키마 생성문을 생성시켜서 복사붙여넣기 하여 schema.sql 파일을 작성한다고 한다.