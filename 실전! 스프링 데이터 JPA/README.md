# 실전-spring-boot-data-jpa
김영한 님의 강의

본 노트는 내가 강의를 듣고 새롭게 배운 것에 대해서만 요약해 적어두는 용도이다.

### Gradle 실행 속도 빠르게 하기
build, execution, deployment > build tools > gradle 설정 창에서 build and run using 옵션과 run tests using 옵션을: ***intelliJ IDEA*** 로 하기

### 로거 사용 주의
**로그를 출력하는 것은 가급력 로거를 활용해 남겨야 한다.**

``` properties
jpa.properties.hibernate.show_sql: true
``` 
sout을 통해 하이버네이트 실행 SQL을 남긴다.
``` properties
logging.level.org.hibernate.SQL: debug
```
logger를 통해 하이버네이트 실행 SQL을 남긴다.

### 쿼리 파라미터 로그 남기기
https://github.com/gavlyukovskiy/spring-boot-data-source-decorator

외부 라이브러리를 활용하여 쿼리 파라미터를 같이 볼 수 있다.

1. 의존성 추가 
``` properties
implementation 'com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.5.7'
```
2. 설정
``` properties
logging.level.org.hibernate.type: trace
```

> 참고: 쿼리 파라미터를 로그로 남기는 외부 라이브러리는 시스템 자원을 사용하므로, 개발 단계에서는 편하게 사용해도 된다. 하지만 운영시스템에 적용하려면 꼭 성능테스트를 하고 사용하는 것이 좋다.
>> (나의 질문) 성능 테스트는 어떻게 하는건가 ?

### 엔티티 작성 시 팁

`@NoArgsConstructor(access = AccessLevel.PROTECTED)` : 기본 생성자 막고 싶은데, JPA 스팩상 PROTECTED로 열어두어야 함.

`@ToString(of = {"id", "username", "age"})` : 가급적 내부 필드만(연관관계 없는 필드만).

### 데이터 확인 테스트 관련

`em.flush();` : DB에 데이터를 반영함.

`em.clear();` : 영속성 컨텍스트를 지움.

persist()를 하고 em.find()를 바로 호출하면, 조회 쿼리를 볼 수 없다. 영속성 컨텍스트에 있는 데이터를 가지고 오기 때문이다. 이는 당연히 개발, 공부 시에만 활용된다.

`@Rollback(false)` : 테스트 한 결과를 commit 시켜 db 테이블에서 결과를 확인할 수 있음.

### 공통 interface
아래와 같이 작성하면 스프링 데이터 JPA가 구현 클래스를 대신 생성해준다.
``` java
public interface MemberRepository extends JpaRepository<Member, Long> {
}
```
MemberRepository는 *스프링 데이터 JPA가 제공하는 공통 인터페이스*인 `JPARepository`를 상속받아 결국은 인터페이스이다. 

그런데 어떻게 동작하는 것일까?

- memberRepository.getClass()를 출력해보면 
class com.sun.proxy.$ProxyXXX 와 같이 출력된다.
- Spring Data JPA 가 proxy 객체인 MemberRepository 구현 클래스를 만들어 injection 해주었기에 동작하는 것이다.
- 어플리케이션 로딩 시점에 JPA 관련 인터페이스를 인식해 구현 클래스를 만들어 준다.

**공통 인터페이스의 구성**

<img width="615" alt="image" src="https://user-images.githubusercontent.com/68107000/149271032-d7776eaf-2c1a-4e70-ac81-d04ab1eca381.png">

여기서 재미있는 부분은 `getOne(ID)` 는 진짜 엔티티가 아닌 가짜 프록시 객체를 찾아오고 원하는 값을 터치해서 꺼낼 때 데이터베이스에 쿼리가 날라가서 값을 가져오게 된다. 또 `findAll(...)` 은 sort나 paging를 파라미터로 주어 실제 퀴리가 날라갈 수 있다.

## [메인1] 쿼리 메소드 기능

### 메소드 이름으로 쿼리 생성
``` java
public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);
}
```
스프링 데이터 JPA는 메소드 이름을 분석해서 JPQL을 생성하고 실행한다.

애플리케이션 로딩 시점에 오류를 인지할 수 있는 것이 스프링 데이터 JPA의 매우 큰 장점이다! 

파라미터 2개까지 이 방법을 사용한다고 한다.

쿼리 메소드 필터 조건 관련 공식 문서: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation

|종류|예시|반환타입|
|:---:|:---:|:---:|
|조회|find...By ,read...By, query...By, get...By <br>(...에 식별, 설명하기 위한 내용, 즉 아무거나 들어가도 됨)||
|COUNT|count...By|long|
|EXISTS|exists...By|boolean|
|삭제|delete...By, remove...By|long|
|DISTINCT|findDistinct, findMemberDistinctBy||
|LIMIT|findFirst3, findFirst, findTop, findTop3||

### NamedQuery

실무에서 Named Query를 직접 등록해서 사용하는 일은 거의 없다.

### @Query - 리파지토리 메소드에 쿼리 정의

대신 `@org.springframework.data.jpa.repository.Query`를 사용해서 repository 메소드에 쿼리를 직접 정의한다.

실행할 메서드에 정적 쿼리를 직접 작성하므로 이름 없는 Named 쿼리라 할 수 있다.

**애플리케이션 로딩(실행) 시점에 문법 오류를 발견할 수 있다.** 애플리케이션 로딩 시점에 `@Query` 안의 정적 쿼리문을 파싱해 SQL 문으로 만들어둔다. 그렇기 때문에 문법 오류를 발견할 수 있다.

파라미터 3개 이상부터 이 방법을 많이 사용한다고 한다.

#### @Query로 단순한 값 하나를 조회하기

``` java
@Query("select m.username from Member m")
List<String> findUsernameList();
```

#### @Query로 DTO 직접 조회하기

DTO로 직접 조회 하려면 JPA의 new 명령어를 사용해야 한다. 그리고 다음과 같이 생성자가 맞는 DTO가 필요하다.

``` java
@Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
List<MemberDto> findMemberDto();
```
``` java
import lombok.Data;

@Data

public class MemberDto {
    private Long id;
    private String username;
    private String teamName;

    public MemberDto(Long id, String username, String teamName) {
        this.id = id;
        this.username = username;
        this.teamName = teamName;
    }
}
```

#### 파라미터 바인딩

``` java
public interface MemberRepository extends JpaRepository<Member, Long> {
    // 이름 기반
    @Query("select m from Member m where m.username = :name")
    Member findMembers(@Param("name") String username);

    //위치 기반
    // @Query("select m from Member m where m.username = ?0") 

    // 컬렉션 파라미터 바인딩, in절 지원
    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") List<String> names);
}
```
- **이름 기반** -> 코드 가독성과 유지보수를 위해 이름 기반 파라미터 바인딩을 사용하자!
- 위치 기반 -> no..!

#### 반환 타입
spring data JPA는 유연한 반환 타입을 지원한다.
``` java
List<Member> findByUsername(String name); //컬렉션
/* 결과가 없을 땐 null이 아닌 빈 컬렉션을 반환함 */

Member findByUsername(String name); //단건
/* 결과가 없을 땐 null을 반환하지만 결과 2건 이상일 경우 예외 발생 */

Optional<Member> findByUsername(String name); //단건 Optional
/* empty(), orElse() 지원, 결과 2건 이상일 경우 예외 발생 */
```

## [메인2] 페이징과 정렬

### 사용 예
``` java
public interface MemberRepository extends Repository<Member, Long> {
    /* 중복된 이름으로 메서드를 선언할 수 없다. 편의상 같은 이름으로 써두었다. */
    Page<Member> findByAge(int age, Pageable pageable); //count 쿼리 사용 
    Slice<Member> findByAge(int age, Pageable pageable); //count 쿼리 사용 안함
    List<Member> findByAge(int age, Pageable pageable); //count 쿼리 사용 안함
    List<Member> findByAge(int age, Sort sort);
}
```
```java
PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username")); // 현재 페이지, 조회할 데이터 수, 추가적으로 정렬 정보
Page<Member> page = memberRepository.findByAge(10, pageRequest); // 위 인터페이스에서 첫번째 메서드 활용 시

// 아래의 모든 메서드 사용 가능
List<Member> content = page.getContent(); //조회된 데이터 
assertThat(content.size()).isEqualTo(3); //조회된 데이터 수 
assertThat(page.getNumber()).isEqualTo(0); //페이지 번호
assertThat(page.isFirst()).isTrue(); //첫번째 항목인가? 
assertThat(page.hasNext()).isTrue(); //다음 페이지가 있는가?
/* 아래의 메서드는 page를 반환 타입으로 가져, count query를 날리기 때문에 가능 */
assertThat(page.getTotalElements()).isEqualTo(5); //전체 데이터 수
assertThat(page.getTotalPages()).isEqualTo(2); //전체 페이지 번호 
```
### 파라미터
- `org.springframework.data.domain.Sort` : 정렬 기능 
- `org.springframework.data.domain.Pageable` : 페이징 기능 (내부에 Sort 포함)
### 반환 타입
- `org.springframework.data.domain.Page` : 추가 count 쿼리 결과를 포함하는 페이징 
- `org.springframework.data.domain.Slice` : 추가 count 쿼리 없이 다음 페이지만 확인 가능 (내부적으로 limit + 1조회)
- `List` (자바 컬렉션): 추가 count 쿼리 없이 결과만 반환


두 번째 파라미터로 받은 Pagable 은 인터페이스다. 따라서 실제 사용할 때는 해당 인터페이스를 구현한 `org.springframework.data.domain.PageRequest` 객체를 사용한다.

페이지는 0부터 시작한다 !

### count query 분리
``` java
@Query(value = “select m from Member m”, 
       countQuery = “select count(m.username) from Member m”)
Page<Member> findMemberAllCountBy(Pageable pageable);
```
### 페이지를 유지하면서 엔티티를 DTO로 변환
```java
Page<MemberDto> dtoPage = page.map(m -> new MemberDto());
```