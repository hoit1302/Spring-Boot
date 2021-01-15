pom.xml 에는 h2, mysql, postgresql와 관련된 의존성이 추가되어있다.

------

**h2**는 in-memory db 를 사용해보았다.

**mysql**은 기존에 설치되어 있어서 db만 새로 만들어서 해보았다.

**postgresql**은 postgresql을 직접 설치하지 않고 docker로 접속해서 실행해보았다.

(docker를 처음으로 설치해보았다. 블로그들이 무슨 말을 하는지 전혀 알 수가 없다...^^)

------

```
# postgres_boot 라는 컨테이너를 만들었음.
docker run -p 5432:5432 -e POSTGRES_PASSWORD=jueun -e POSTGRES_USER=jueun -e POSTGRES_DB=keesundb --name postgres_boot -d postgres

# postgres_boot 의 bash를 선택해서 interactive 모드로 실행
docker exec -i -t postgres_boot bash

==========================================================================
# window의 경우 이렇게 하면 됨. 관련 내용 질문 응답은 아래 #
psql -U jueun keesundb
--------------------------------------------------------------------------
# 사용자를 postgres로 바꿔서
su - postgres
# keesundb을 사용
psql keesundb
==========================================================================
# 데이터베이스 조회
\list 또는 \l

테이블 조회
\dt

쿼리
SELECT * FROM account;

```

[#](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8/lecture/13558?tab=question&q=5650)