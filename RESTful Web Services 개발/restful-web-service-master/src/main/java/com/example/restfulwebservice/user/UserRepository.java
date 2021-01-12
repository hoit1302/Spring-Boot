package com.example.restfulwebservice.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // annotation을 등록하는 것만으로 CRUD에 해당하는 작업을 할 수 있다.
public interface UserRepository extends JpaRepository<User, Integer> {
    // JpaRepository는 User 엔티티를 다룰 것이고, 기본키 id는 int형
}
