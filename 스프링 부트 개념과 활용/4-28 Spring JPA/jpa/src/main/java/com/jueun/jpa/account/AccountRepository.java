package com.jueun.jpa.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

//    @Query(nativeQuery = true, value="직접쓸 수도 있다")
//    JPQL 문법을 쓰는 것도 언급하심.
    Optional<Account> findByUsername(String username);
}
